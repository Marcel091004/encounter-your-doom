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
}

