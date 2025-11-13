package org.cool.encounteryourdoom.Service;

import org.cool.encounteryourdoom.Mapper.CreatureMapper;
import org.cool.encounteryourdoom.Repository.CreatureRepository;
import org.cool.encounteryourdoom.Repository.Filter.CreatureParameterFilter;
import org.cool.encounteryourdoom.model.CreatureEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.openapitools.model.Creature;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreatureServiceTest {
	private CreatureRepository creatureRepository;
	private CreatureMapper creatureMapper;
	private CreatureService creatureService;

	@BeforeEach
	void setUp() {
		creatureRepository = mock(CreatureRepository.class);
		creatureMapper = mock(CreatureMapper.class);
		creatureService = new CreatureService(creatureRepository, creatureMapper);
	}

	@Test
	void getAllCreatures_shouldReturnMappedList() {
		CreatureParameterFilter filter = mock(CreatureParameterFilter.class);
		List<CreatureEntity> entities = Arrays.asList(new CreatureEntity(), new CreatureEntity());
		List<Creature> creatures = Arrays.asList(new Creature(), new Creature());
		when(creatureRepository.findCreaturesByFilters(filter)).thenReturn(entities);
		when(creatureMapper.toCreatureList(entities)).thenReturn(creatures);

		List<Creature> result = creatureService.getAllCreatures(filter);
		assertEquals(creatures, result);
	}

	@Test
	void getCreatureByID_shouldReturnMappedCreature() {
		UUID id = UUID.randomUUID();
		CreatureEntity entity = new CreatureEntity();
		Creature creature = new Creature();
		when(creatureRepository.findById(id)).thenReturn(Optional.of(entity));
		when(creatureMapper.toCreature(entity)).thenReturn(creature);

		Creature result = creatureService.getCreatureByID(id);
		assertEquals(creature, result);
	}

	@Test
	void getCreatureByID_shouldReturnNullIfNotFound() {
		UUID id = UUID.randomUUID();
		when(creatureRepository.findById(id)).thenReturn(Optional.empty());

		when(creatureMapper.toCreature(null)).thenReturn(null);

		Creature result = creatureService.getCreatureByID(id);
		assertNull(result);
	}

	@Test
	void updateCreatureByID_shouldUpdateEntity() {
		UUID id = UUID.randomUUID();
		Creature creature = new Creature();
		CreatureEntity oldEntity = new CreatureEntity();
		oldEntity.setId(id);
		CreatureEntity newEntity = new CreatureEntity();
		when(creatureRepository.findById(id)).thenReturn(Optional.of(oldEntity));
		when(creatureMapper.toCreatureEntity(creature)).thenReturn(newEntity);

		creatureService.updateCreatureByID(id, creature);
		verify(creatureRepository).save(newEntity);
		assertEquals(id, newEntity.getId());
	}

	@Test
	void createCreature_shouldSaveEntityAndReturnUUID() {
		Creature creature = new Creature();
		CreatureEntity entity = new CreatureEntity();
		when(creatureMapper.toCreatureEntity(creature)).thenReturn(entity);
		when(creatureRepository.save(any(CreatureEntity.class))).thenReturn(entity);

		UUID result = creatureService.createCreature(creature);

		assertNotNull(result);
		ArgumentCaptor<CreatureEntity> captor = ArgumentCaptor.forClass(CreatureEntity.class);
		verify(creatureRepository).save(captor.capture());
		assertEquals(result, captor.getValue().getId());
	}

	@Test
	void createCreature_shouldSetIdOnEntity() {
		Creature creature = new Creature();
		CreatureEntity entity = new CreatureEntity();
		when(creatureMapper.toCreatureEntity(creature)).thenReturn(entity);
		when(creatureRepository.save(any(CreatureEntity.class))).thenReturn(entity);

		UUID result = creatureService.createCreature(creature);
		assertEquals(result, entity.getId());
	}

	@Test
	void getRandomCreature_shouldReturnRandomCreature() {
		CreatureParameterFilter filter = mock(CreatureParameterFilter.class);
		CreatureEntity entity1 = new CreatureEntity();
		CreatureEntity entity2 = new CreatureEntity();
		List<CreatureEntity> entities = Arrays.asList(entity1, entity2);
		Creature creature = new Creature();
		when(creatureRepository.findCreaturesByFilters(filter)).thenReturn(entities);
		when(creatureMapper.toCreature(any())).thenReturn(creature);

		Creature result = creatureService.getRandomCreature(filter);
		assertNotNull(result);
		assertEquals(creature, result);
	}

	@Test
	void getRandomCreature_shouldReturnNullIfEmpty() {
		CreatureParameterFilter filter = mock(CreatureParameterFilter.class);
		when(creatureRepository.findCreaturesByFilters(filter)).thenReturn(List.of());

		Creature result = creatureService.getRandomCreature(filter);
		assertNull(result);
	}
}

