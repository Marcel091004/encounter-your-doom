package org.cool.encounteryourdoom.repository.implementierungen;

import org.cool.encounteryourdoom.repository.filter.CreatureParameterFilter;
import org.cool.encounteryourdoom.model.CreatureEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openapitools.model.Rarity;
import org.openapitools.model.Region;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class CreatureRepositoryImplTest {

	@Mock
	private MongoTemplate mongoTemplate;

	@InjectMocks
	private CreatureRepositoryImpl repository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindCreaturesByFilters_NullFilter() {
		// given
		List<CreatureEntity> expected = List.of(new CreatureEntity());
		when(mongoTemplate.find(any(Query.class), eq(CreatureEntity.class))).thenReturn(expected);

		// when
		List<CreatureEntity> result = repository.findCreaturesByFilters(null);

		// then
		assertEquals(expected, result);
		ArgumentCaptor<Query> queryCaptor = ArgumentCaptor.forClass(Query.class);
		verify(mongoTemplate).find(queryCaptor.capture(), eq(CreatureEntity.class));

		Query captured = queryCaptor.getValue();
		assertTrue(captured.getQueryObject().isEmpty(), "Query should be empty when filter is null");
	}

	@Test
	void testFindCreaturesByFilters_WithRegionAndRarity() {
		// given
		CreatureParameterFilter filter = new CreatureParameterFilter();
		filter.setRegion(Region.FOREST);
		filter.setRarity(Rarity.RARE);

		List<CreatureEntity> expected = List.of(new CreatureEntity(), new CreatureEntity());
		when(mongoTemplate.find(any(Query.class), eq(CreatureEntity.class))).thenReturn(expected);

		// when
		List<CreatureEntity> result = repository.findCreaturesByFilters(filter);

		// then
		assertEquals(2, result.size());
		ArgumentCaptor<Query> queryCaptor = ArgumentCaptor.forClass(Query.class);
		verify(mongoTemplate).find(queryCaptor.capture(), eq(CreatureEntity.class));

		var queryObject = queryCaptor.getValue().getQueryObject();
		assertEquals(Region.FOREST, queryObject.get("region"));
		assertEquals(Rarity.RARE, queryObject.get("rarity"));
	}

	@Test
	void testFindCreaturesByFilters_WithCR() {
		// given
		CreatureParameterFilter filter = new CreatureParameterFilter();
		filter.setCr("10");

		when(mongoTemplate.find(any(Query.class), eq(CreatureEntity.class))).thenReturn(List.of(new CreatureEntity()));

		// when
		List<CreatureEntity> result = repository.findCreaturesByFilters(filter);

		// then
		assertEquals(1, result.size());
		ArgumentCaptor<Query> queryCaptor = ArgumentCaptor.forClass(Query.class);
		verify(mongoTemplate).find(queryCaptor.capture(), eq(CreatureEntity.class));

		assertEquals("10", queryCaptor.getValue().getQueryObject().get("cr"));
	}
}
