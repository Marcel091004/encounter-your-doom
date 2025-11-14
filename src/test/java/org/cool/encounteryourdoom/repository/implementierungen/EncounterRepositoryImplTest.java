package org.cool.encounteryourdoom.repository.implementierungen;

import org.cool.encounteryourdoom.repository.filter.EncounterParameterFilter;
import org.cool.encounteryourdoom.model.EncounterEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.openapitools.model.DifficultyLevel;
import org.openapitools.model.Rarity;
import org.openapitools.model.Region;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EncounterRepositoryImplTest {
	private MongoTemplate mongoTemplate;
	private EncounterRepositoryImpl repository;

	@BeforeEach
	void setUp() {
		mongoTemplate = mock(MongoTemplate.class);
		repository = new EncounterRepositoryImpl(mongoTemplate);
	}

	@Test
	void findEncountersByFilters_shouldBuildQueryWithAllParameters() {
		EncounterParameterFilter filter = new EncounterParameterFilter();
		filter.setRegion(Region.FOREST);
		filter.setRarity(Rarity.COMMON);
		filter.setDifficultyLevel(DifficultyLevel.EASY);
		filter.setPartyLevel(3);
		List<EncounterEntity> expected = List.of(new EncounterEntity());
		when(mongoTemplate.find(any(Query.class), eq(EncounterEntity.class))).thenReturn(expected);

		List<EncounterEntity> result = repository.findEncountersByFilters(filter);
		assertEquals(expected, result);
		ArgumentCaptor<Query> captor = ArgumentCaptor.forClass(Query.class);
		verify(mongoTemplate).find(captor.capture(), eq(EncounterEntity.class));
		Query query = captor.getValue();
		String queryStr = query.getQueryObject().toString();
		assertTrue(queryStr.contains("region"));
		assertTrue(queryStr.contains("rarity"));
		assertTrue(queryStr.contains("difficultyLevel"));
		assertTrue(queryStr.contains("partyLevel"));
	}

	@Test
	void findEncountersByFilters_shouldReturnEmptyListIfFilterIsNull() {
		List<EncounterEntity> result = repository.findEncountersByFilters(null);
		//Information: Der Filter muss angegeben sein das die Abfrage funktioniert
		//→ sprich alle Parameter null, aber Filter angelegt und übergeben dann kommen alle Encounter
		assertTrue(result.isEmpty());
		verify(mongoTemplate, never()).find(any(), any());
	}

	@Test
	void findEncountersByFilters_shouldBuildQueryWithPartialParameters() {
		EncounterParameterFilter filter = new EncounterParameterFilter();
		filter.setRegion(Region.FOREST);
		List<EncounterEntity> expected = List.of(new EncounterEntity());
		when(mongoTemplate.find(any(Query.class), eq(EncounterEntity.class))).thenReturn(expected);

		List<EncounterEntity> result = repository.findEncountersByFilters(filter);
		assertEquals(expected, result);
		ArgumentCaptor<Query> captor = ArgumentCaptor.forClass(Query.class);
		verify(mongoTemplate).find(captor.capture(), eq(EncounterEntity.class));
		Query query = captor.getValue();
		String queryStr = query.getQueryObject().toString();
		assertTrue(queryStr.contains("region"));
		assertFalse(queryStr.contains("rarity"));
		assertFalse(queryStr.contains("difficultyLevel"));
		assertFalse(queryStr.contains("partyLevel"));
	}
}

