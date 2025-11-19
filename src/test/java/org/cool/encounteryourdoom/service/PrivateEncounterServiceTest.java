package org.cool.encounteryourdoom.service;

import org.cool.encounteryourdoom.mapper.PrivateEncounterMapper;
import org.cool.encounteryourdoom.model.EncounterEntity;
import org.cool.encounteryourdoom.model.PrivateEncounterEntity;
import org.cool.encounteryourdoom.repository.PrivateEncounterRepository;
import org.cool.encounteryourdoom.repository.filter.PrivateEncounterParameterFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PrivateEncounterServiceTest {

    private PrivateEncounterService privateEncounterService;
    private PrivateEncounterMapper privateEncounterMapper;
    private PrivateEncounterRepository privateEncounterRepository;

    @BeforeEach
    void setUp() {
        privateEncounterRepository = mock(PrivateEncounterRepository.class);
        privateEncounterMapper = mock(PrivateEncounterMapper.class);
        privateEncounterService = new PrivateEncounterService(privateEncounterRepository, privateEncounterMapper);
    }

    @Nested
    class GetEncounterList {
        @Test
        void shouldReturnMappedList() {
            PrivateEncounterParameterFilter filter = mock(PrivateEncounterParameterFilter.class);

            List<PrivateEncounterEntity> privateEncounters = Arrays.asList(new PrivateEncounterEntity(), new PrivateEncounterEntity());
            List<EncounterEntity> encounters = Arrays.asList(new EncounterEntity(), new EncounterEntity());

            when(privateEncounterRepository.findEncountersByFilters(filter)).thenReturn(privateEncounters);
            when(privateEncounterMapper.toEncounterEntity(privateEncounters)).thenReturn(encounters);

            List<EncounterEntity> encounterEntities = privateEncounterService.getEncounterList(filter);

            assertEquals(encounters, encounterEntities);
        }

		@Test
		void shouldHandleWrongFilterGracefully() {
			PrivateEncounterParameterFilter filter = mock(PrivateEncounterParameterFilter.class);

			when(privateEncounterRepository.findEncountersByFilters(filter)).thenReturn(Arrays.asList());
			when(privateEncounterMapper.toEncounterEntity(Arrays.asList())).thenReturn(Arrays.asList());

			List<EncounterEntity> encounterEntities = privateEncounterService.getEncounterList(filter);

			assertEquals(0, encounterEntities.size());
		}
    }

	@Nested
	class GetEncounterByID {
		@Test
		void shouldReturnMappedEncounter() {
			UUID encounterId = UUID.randomUUID();
			UUID userId = UUID.randomUUID();

			PrivateEncounterEntity privateEncounterEntity = new PrivateEncounterEntity();
			EncounterEntity encounterEntity = new EncounterEntity();

			when(privateEncounterRepository.findById(encounterId)).thenReturn(java.util.Optional.of(privateEncounterEntity));
			when(privateEncounterMapper.toEncounterEntity(privateEncounterEntity)).thenReturn(encounterEntity);

			EncounterEntity result = privateEncounterService.getEncounterByID(userId, encounterId);

			assertEquals(encounterEntity, result);
		}

		@Test
		void shouldHandleNonExistentEncounterGracefully() {
			UUID encounterId = UUID.randomUUID();
			UUID userId = UUID.randomUUID();

			when(privateEncounterRepository.findById(encounterId)).thenReturn(java.util.Optional.empty());

			try {
				privateEncounterService.getEncounterByID(userId, encounterId);
			} catch (Exception e) {
				assertEquals(java.util.NoSuchElementException.class, e.getClass());
			}
		}
	}
	@Nested
	class UpdateEncounterByID {
		@Test
		void shouldUpdateEncounterSuccessfully() {
			UUID encounterId = UUID.randomUUID();
			UUID userId = UUID.randomUUID();
			org.openapitools.model.Encounter updatedEncounter = new org.openapitools.model.Encounter();

			PrivateEncounterEntity existingEntity = new PrivateEncounterEntity();
			PrivateEncounterEntity updatedEntity = new PrivateEncounterEntity();

			when(privateEncounterRepository.findById(encounterId)).thenReturn(java.util.Optional.of(existingEntity));
			when(privateEncounterMapper.toPrivatEncounterEntity(updatedEncounter)).thenReturn(updatedEntity);

			privateEncounterService.updateEncounterByID(userId, encounterId, updatedEncounter);

			// Verify that save was called with the updated entity
			org.mockito.Mockito.verify(privateEncounterRepository).save(updatedEntity);
		}

		@Test
		void shouldHandleNonExistentEncounterOnUpdateGracefully() {
			UUID encounterId = UUID.randomUUID();
			UUID userId = UUID.randomUUID();
			org.openapitools.model.Encounter updatedEncounter = new org.openapitools.model.Encounter();

			when(privateEncounterRepository.findById(encounterId)).thenReturn(java.util.Optional.empty());

			try {
				privateEncounterService.updateEncounterByID(userId, encounterId, updatedEncounter);
			} catch (Exception e) {
				assertEquals(java.util.NoSuchElementException.class, e.getClass());
			}
		}

		@Test
		void shouldHandleMappingFailureGracefully() {
			UUID encounterId = UUID.randomUUID();
			UUID userId = UUID.randomUUID();
			org.openapitools.model.Encounter updatedEncounter = new org.openapitools.model.Encounter();

			PrivateEncounterEntity existingEntity = new PrivateEncounterEntity();

			when(privateEncounterRepository.findById(encounterId)).thenReturn(java.util.Optional.of(existingEntity));
			when(privateEncounterMapper.toPrivatEncounterEntity(updatedEncounter)).thenThrow(new RuntimeException("Mapping failed"));

			try {
				privateEncounterService.updateEncounterByID(userId, encounterId, updatedEncounter);
			} catch (Exception e) {
				assertEquals(RuntimeException.class, e.getClass());
				assertEquals("Mapping failed", e.getMessage());
			}
		}
	}
}

