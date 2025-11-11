package org.cool.encounteryourdoom.API;

import org.cool.encounteryourdoom.Controller.EncounterController;
import org.cool.encounteryourdoom.Service.EncounterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.openapitools.model.DifficultyLevel;
import org.openapitools.model.Encounter;
import org.openapitools.model.Rarity;
import org.openapitools.model.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class EncounterControllerTest {
    private EncounterService encounterService;
    private EncounterController encounterController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        encounterService = mock(EncounterService.class);
        encounterController = new EncounterController(encounterService);
    }

    @Test
    void getAllPublicEncounters_shouldReturnEncountersAndCallServiceWithCorrectFilter() {
        Region region = Region.FOREST;
        Rarity rarity = Rarity.COMMON;
        DifficultyLevel difficultyLevel = DifficultyLevel.EASY;
        Integer partyLevel = 3;
        List<Encounter> encounters = Arrays.asList(new Encounter(), new Encounter());
        when(encounterService.getAllPublicEncounters(any())).thenReturn(encounters);

        ResponseEntity<List<Encounter>> response = encounterController.getAllPublicEncounters(region, rarity, difficultyLevel, partyLevel);

        assertEquals(ResponseEntity.ok(encounters), response);
        ArgumentCaptor<org.cool.encounteryourdoom.Repository.Filter.EncounterParameterFilter> captor = ArgumentCaptor.forClass(org.cool.encounteryourdoom.Repository.Filter.EncounterParameterFilter.class);
        verify(encounterService).getAllPublicEncounters(captor.capture());
        org.cool.encounteryourdoom.Repository.Filter.EncounterParameterFilter filter = captor.getValue();
        assertEquals(region, filter.getRegion());
        assertEquals(rarity, filter.getRarity());
        assertEquals(difficultyLevel, filter.getDifficultyLevel());
        assertEquals(partyLevel, filter.getPartyLevel());
    }

    @Test
    void getAllPublicEncounters_shouldHandleNullParameters() {
        List<Encounter> encounters = Arrays.asList(new Encounter());
        when(encounterService.getAllPublicEncounters(any())).thenReturn(encounters);

        ResponseEntity<List<Encounter>> response = encounterController.getAllPublicEncounters(null, null, null, null);

        assertEquals(ResponseEntity.ok(encounters), response);
        ArgumentCaptor<org.cool.encounteryourdoom.Repository.Filter.EncounterParameterFilter> captor = ArgumentCaptor.forClass(org.cool.encounteryourdoom.Repository.Filter.EncounterParameterFilter.class);
        verify(encounterService).getAllPublicEncounters(captor.capture());
        org.cool.encounteryourdoom.Repository.Filter.EncounterParameterFilter filter = captor.getValue();
        assertNull(filter.getRegion());
        assertNull(filter.getRarity());
        assertNull(filter.getDifficultyLevel());
        assertNull(filter.getPartyLevel());
    }
}

