package org.cool.encounteryourdoom.repository.implementierungen;

import org.cool.encounteryourdoom.model.EncounterEntity;
import org.cool.encounteryourdoom.model.PrivateEncounterEntity;
import org.cool.encounteryourdoom.repository.filter.PrivateEncounterParameterFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.openapitools.model.DifficultyLevel;
import org.openapitools.model.PrivateEncounter;
import org.openapitools.model.Rarity;
import org.openapitools.model.Region;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class PrivateEncounterReositoryImplTest {
	private MongoTemplate mongoTemplate;
	private PrivateEncounterRepositoryImpl repository;

	@BeforeEach
	void setUp() {
		mongoTemplate = mock(MongoTemplate.class);
		repository = new PrivateEncounterRepositoryImpl(mongoTemplate);
	}

	@Test
	void findEncountersByFilters_shouldBuildQueryWithAllParameters() {
		PrivateEncounterParameterFilter filter = new PrivateEncounterParameterFilter();

		UUID userId = UUID.randomUUID();

		filter.setUserId(userId);
		filter.setRegion(Region.FOREST);
		filter.setRarity(Rarity.COMMON);
		filter.setDifficultyLevel(DifficultyLevel.EASY);
		filter.setPartyLevel(3);

		List<PrivateEncounterEntity> expected = List.of(new PrivateEncounterEntity());
		when(mongoTemplate.find(any(Query.class), eq(PrivateEncounterEntity.class))).thenReturn(expected);

		List<PrivateEncounterEntity> result = repository.findEncountersByFilters(filter);
		assertEquals(expected, result);

		ArgumentCaptor<Query> captor = ArgumentCaptor.forClass(Query.class);
		verify(mongoTemplate).find(captor.capture(), eq(PrivateEncounterEntity.class));
		Query query = captor.getValue();
		String queryStr = query.getQueryObject().toString();
		assertTrue(queryStr.contains("userId"));
		assertTrue(queryStr.contains("region"));
		assertTrue(queryStr.contains("rarity"));
		assertTrue(queryStr.contains("difficultyLevel"));
		assertTrue(queryStr.contains("partyLevel"));
	}

	@Test
	void findEncountersByFilters_shouldReturnEmptyListIfFilterIsNull() {
		List<PrivateEncounterEntity> result = repository.findEncountersByFilters(null);
		//Information: Der Filter muss angegeben sein das die Abfrage funktioniert
		//→ sprich alle Parameter null, aber Filter angelegt und übergeben dann kommen alle Encounter
		assertTrue(result.isEmpty());
		verify(mongoTemplate, never()).find(any(), any());
	}

	@Test
	void findEncountersByFilters_shouldBuildQueryWithPartialParameters() {
		PrivateEncounterParameterFilter filter = new PrivateEncounterParameterFilter();
		filter.setUserId(UUID.randomUUID());
		filter.setRegion(Region.FOREST);
		List<PrivateEncounterEntity> expected = List.of(new PrivateEncounterEntity());
		when(mongoTemplate.find(any(Query.class), eq(PrivateEncounterEntity.class))).thenReturn(expected);

		List<PrivateEncounterEntity> result = repository.findEncountersByFilters(filter);
		assertEquals(expected, result);
		ArgumentCaptor<Query> captor = ArgumentCaptor.forClass(Query.class);
		verify(mongoTemplate).find(captor.capture(), eq(PrivateEncounterEntity.class));
		Query query = captor.getValue();
		String queryStr = query.getQueryObject().toString();
		assertTrue(queryStr.contains("userId"));
		assertTrue(queryStr.contains("region"));
		assertFalse(queryStr.contains("rarity"));
		assertFalse(queryStr.contains("difficultyLevel"));
		assertFalse(queryStr.contains("partyLevel"));
	}
}
