package org.cool.encounteryourdoom.Service;

import org.cool.encounteryourdoom.Mapper.EncounterMapper;
import org.cool.encounteryourdoom.Repository.EncounterRepository;
import org.cool.encounteryourdoom.Repository.Filter.EncounterParameterFilter;
import org.cool.encounteryourdoom.model.EncounterEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.openapitools.model.Encounter;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EncounterServiceTest {
    private EncounterRepository encounterRepository;
    private EncounterMapper encounterMapper;
    private EncounterService encounterService;

    @BeforeEach
    void setUp() {
        encounterRepository = mock(EncounterRepository.class);
        encounterMapper = mock(EncounterMapper.class);
        encounterService = new EncounterService(encounterRepository, encounterMapper);
    }

    @Test
    void getAllPublicEncounters_shouldReturnMappedList() {
        EncounterParameterFilter filter = mock(EncounterParameterFilter.class);
        List<EncounterEntity> entities = Arrays.asList(new EncounterEntity(), new EncounterEntity());
        List<Encounter> encounters = Arrays.asList(new Encounter(), new Encounter());
        when(encounterRepository.findEncountersByFilters(filter)).thenReturn(entities);
        when(encounterMapper.toEncounterList(entities)).thenReturn(encounters);

        List<Encounter> result = encounterService.getAllPublicEncounters(filter);
        assertEquals(encounters, result);
    }

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
    void getRandomEncounter_shouldReturnNullIfNoEncounters() {
        when(encounterRepository.findEncountersByFilters(any(EncounterParameterFilter.class)))
                .thenReturn(List.of());
        Encounter result = encounterService.getRandomEncounter();
        assertNull(result);
    }

    @Test
    void getRandomEncounter_shouldReturnMappedEncounterIfPresent() {
        EncounterEntity entity = new EncounterEntity();
        Encounter mapped = new Encounter();
        when(encounterRepository.findEncountersByFilters(any(EncounterParameterFilter.class)))
                .thenReturn(List.of(entity));
        when(encounterMapper.toEncounter(entity)).thenReturn(mapped);
        Encounter result = encounterService.getRandomEncounter();
        assertEquals(mapped, result);
    }

    @Test
    void getRandomEncounter_shouldReturnAnyEncounterFromList() {
        EncounterEntity entity1 = new EncounterEntity();
        EncounterEntity entity2 = new EncounterEntity();
        Encounter mapped1 = new Encounter();
        Encounter mapped2 = new Encounter();
        List<EncounterEntity> entities = List.of(entity1, entity2);
        when(encounterRepository.findEncountersByFilters(any(EncounterParameterFilter.class)))
                .thenReturn(entities);
        when(encounterMapper.toEncounter(entity1)).thenReturn(mapped1);
        when(encounterMapper.toEncounter(entity2)).thenReturn(mapped2);
        // Mehrfach testen, um Zufall zu prüfen
        boolean found1 = false, found2 = false;
        for (int i = 0; i < 20; i++) {
            Encounter result = encounterService.getRandomEncounter();
			if (result.equals(mapped1)) found1 = true;
			if (result.equals(mapped2)) found2 = true;
			if (found1 && found2) break;
        }
        assertTrue(found1 && found2, "Beide möglichen Encounters sollten zurückgegeben werden können");
    }

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
