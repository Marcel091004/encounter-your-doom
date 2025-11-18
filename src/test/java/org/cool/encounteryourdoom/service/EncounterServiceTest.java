package org.cool.encounteryourdoom.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.cool.encounteryourdoom.TestDataHelper;
import org.cool.encounteryourdoom.mapper.EncounterMapper;
import org.cool.encounteryourdoom.mapper.PrivateEncounterMapper;
import org.cool.encounteryourdoom.model.EncounterEntity;
import org.cool.encounteryourdoom.model.PrivateEncounterEntity;
import org.cool.encounteryourdoom.repository.EncounterRepository;
import org.cool.encounteryourdoom.repository.PrivateEncounterRepository;
import org.cool.encounteryourdoom.repository.filter.EncounterParameterFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.Encounter;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EncounterServiceTest {
	private EncounterRepository encounterRepository;
	private PrivateEncounterRepository privateEncounterRepository;
	private EncounterMapper encounterMapper;
	private PrivateEncounterMapper privatEncounterMapper;
	private EncounterService encounterService;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	void setUp() {
		encounterRepository = mock(EncounterRepository.class);
		privateEncounterRepository = mock(PrivateEncounterRepository.class);

		encounterMapper = mock(EncounterMapper.class);
		privatEncounterMapper = mock(PrivateEncounterMapper.class);

		encounterService = new EncounterService(encounterRepository, privateEncounterRepository, encounterMapper, privatEncounterMapper);
	}

	@Nested
	class GetAllEncountersTests {
		@Test
		void shouldReturnMappedList() {
			EncounterParameterFilter filter = mock(EncounterParameterFilter.class);
			List<EncounterEntity> entities = Arrays.asList(new EncounterEntity(), new EncounterEntity());
			List<Encounter> encounters = Arrays.asList(new Encounter(), new Encounter());
			when(encounterRepository.findEncountersByFilters(filter)).thenReturn(entities);
			when(encounterMapper.toEncounterList(entities)).thenReturn(encounters);

			List<Encounter> result = encounterService.getAllPublicEncounters(filter);
			assertEquals(encounters, result);
		}

		@Test
		void shouldReturnEmptyListIfNoEncounters() {
			EncounterParameterFilter filter = mock(EncounterParameterFilter.class);
			when(encounterRepository.findEncountersByFilters(filter)).thenReturn(List.of());
			when(encounterMapper.toEncounterList(List.of())).thenReturn(List.of());

			List<Encounter> result = encounterService.getAllPublicEncounters(filter);
			assertTrue(result.isEmpty());
		}

		@Test
		void shouldHandle400BadRequest() {
			EncounterParameterFilter filter = mock(EncounterParameterFilter.class);
			when(encounterRepository.findEncountersByFilters(filter)).thenThrow(new IllegalArgumentException("Bad Request"));

			assertThrows(IllegalArgumentException.class, () -> {
				encounterService.getAllPublicEncounters(filter);
			});
		}
	}

	@Nested
	class GetEncounterByIdTests {
		@Test
		void getEncounterById_shouldReturnMappedEncounterIfFound() {
			UUID id = UUID.randomUUID();
			EncounterEntity entity = new EncounterEntity();
			Encounter mapped = new Encounter();
			when(encounterRepository.findById(id)).thenReturn(java.util.Optional.of(entity));
			when(encounterMapper.toEncounter(entity)).thenReturn(mapped);

			Encounter result = encounterService.getEncounterById(id);
			assertEquals(mapped, result);
		}

		@Test
		void getEncounterById_shouldThrowExceptionIfNotFound() {
			UUID id = UUID.randomUUID();
			when(encounterRepository.findById(id)).thenReturn(java.util.Optional.empty());

			assertThrows(java.util.NoSuchElementException.class, () -> {
				encounterService.getEncounterById(id);
			});
		}
	}

	@Nested
	class CreateEncounterTests {
		@Test
		void createEncounter_shouldSaveEntityAndReturnUUID() {
			Encounter encounter = new Encounter();
			EncounterEntity entity = new EncounterEntity();
			when(encounterMapper.toEncounterEntity(encounter)).thenReturn(entity);
			when(encounterRepository.save(any(EncounterEntity.class))).thenReturn(entity);

			UUID result = encounterService.createEncounter(encounter);

			assertNotNull(result);
			ArgumentCaptor<EncounterEntity> captor = ArgumentCaptor.forClass(EncounterEntity.class);
			verify(encounterRepository).save(captor.capture());
			assertEquals(result, captor.getValue().getId());
		}

		@Test
		void createEncounter_shouldSetIdOnEntity() {
			Encounter encounter = new Encounter();
			EncounterEntity entity = new EncounterEntity();
			when(encounterMapper.toEncounterEntity(encounter)).thenReturn(entity);
			when(encounterRepository.save(any(EncounterEntity.class))).thenReturn(entity);

			UUID result = encounterService.createEncounter(encounter);
			assertEquals(result, entity.getId());
		}

		@Test
		void createEncounter_shouldThrowExceptionIfEncounterIsNull() {
			assertThrows(NullPointerException.class, () -> {
				encounterService.createEncounter(null);
			});
		}
	}

	@Nested
	class GetRandomEncounterTests {
		@Test
		void shouldReturnNullIfNoEncounters() {
			when(encounterRepository.findEncountersByFilters(any(EncounterParameterFilter.class)))
					.thenReturn(List.of());
			Encounter result = encounterService.getRandomEncounter();
			assertNull(result);
		}

		@Test
		void shouldReturnMappedEncounterIfPresent() {
			EncounterEntity entity = new EncounterEntity();
			Encounter mapped = new Encounter();
			when(encounterRepository.findEncountersByFilters(any(EncounterParameterFilter.class)))
					.thenReturn(List.of(entity));
			when(encounterMapper.toEncounter(entity)).thenReturn(mapped);
			Encounter result = encounterService.getRandomEncounter();
			assertEquals(mapped, result);
		}

		@Test
		void shouldHandleWrongFilter() {
			when(encounterRepository.findEncountersByFilters(any(EncounterParameterFilter.class)))
					.thenThrow(new IllegalArgumentException("Bad Filter"));
			assertThrows(IllegalArgumentException.class, () -> {
				encounterService.getRandomEncounter();
			});
		}
	}

	@Nested
	class UpdateEncounterTests {
		@Test
		void updateEncounter_shouldUpdateExistingEncounter() {
			UUID id = UUID.randomUUID();
			Encounter encounter = new Encounter();
			EncounterEntity existingEntity = new EncounterEntity();
			EncounterEntity mappedEntity = new EncounterEntity();
			when(encounterRepository.findById(id)).thenReturn(java.util.Optional.of(existingEntity));
			when(encounterMapper.toEncounterEntity(encounter)).thenReturn(mappedEntity);
			when(encounterRepository.save(mappedEntity)).thenReturn(mappedEntity);

			encounterService.updateEncounter(id, encounter);

			assertEquals(id, mappedEntity.getId());
			verify(encounterRepository).save(mappedEntity);
		}

		@Test
		void updateEncounter_shouldThrowExceptionIfNotFound() {
			UUID id = UUID.randomUUID();
			Encounter encounter = new Encounter();
			when(encounterRepository.findById(id)).thenReturn(java.util.Optional.empty());
			IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
					encounterService.updateEncounter(id, encounter));
			assertTrue(ex.getMessage().contains(id.toString()));
		}

		@Test
		void updateEncounter_shouldSetIdFromParameter() {
			UUID id = UUID.randomUUID();
			Encounter encounter = new Encounter();
			EncounterEntity existingEntity = new EncounterEntity();
			EncounterEntity mappedEntity = new EncounterEntity();
			mappedEntity.setId(UUID.randomUUID()); // andere ID
			when(encounterRepository.findById(id)).thenReturn(java.util.Optional.of(existingEntity));
			when(encounterMapper.toEncounterEntity(encounter)).thenReturn(mappedEntity);
			when(encounterRepository.save(mappedEntity)).thenReturn(mappedEntity);

			encounterService.updateEncounter(id, encounter);

			assertEquals(id, mappedEntity.getId());
		}

		@Test
		void updateEncounter_shouldThrowExceptionIfEncounterIsNull() {
			UUID id = UUID.randomUUID();
			EncounterEntity existingEntity = new EncounterEntity();
			when(encounterRepository.findById(id)).thenReturn(java.util.Optional.of(existingEntity));
			assertThrows(NullPointerException.class, () ->
					encounterService.updateEncounter(id, null));
		}
	}

	@Nested
	class MoveToPrivateTests {
		@Test
		void moveToPrivate_shouldMoveEncounterSuccessfully() throws JsonProcessingException {
			UUID id = UUID.randomUUID();
			EncounterEntity publicEntity = objectMapper.readValue(TestDataHelper.getEncounterJson(), EncounterEntity.class);
			PrivateEncounterEntity privateEntity = objectMapper.readValue(TestDataHelper.getPrivateEncounterJson(), PrivateEncounterEntity.class);

			when(encounterMapper.toEncounterEntity(publicEntity)).thenReturn(publicEntity);
			when(privatEncounterMapper.toPrivatEncounterEntity(publicEntity)).thenReturn(privateEntity);

			when(encounterRepository.findById(id)).thenReturn(java.util.Optional.of(publicEntity));
			when(privatEncounterMapper.toPrivatEncounterEntity(encounterMapper.toEncounterEntity(publicEntity))).thenReturn(privateEntity);

			encounterService.moveEncounterToUserSpace(id, UUID.fromString("2511c53f-3e19-4c31-b153-ece0817eb2b8"));

			ArgumentCaptor<PrivateEncounterEntity> captor = ArgumentCaptor.forClass(PrivateEncounterEntity.class);
			verify(privateEncounterRepository).save(captor.capture());
			PrivateEncounterEntity savedEntity = captor.getValue();
			assertEquals(privateEntity, savedEntity);
		}

		@Test
		void moveToPrivate_shouldThrowExceptionIfNotFound() {
			UUID id = UUID.randomUUID();
			when(encounterRepository.findById(id)).thenReturn(java.util.Optional.empty());
			IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
					encounterService.moveEncounterToUserSpace(id, UUID.randomUUID()));
			assertTrue(ex.getMessage().contains(id.toString()));
		}
	}
}
