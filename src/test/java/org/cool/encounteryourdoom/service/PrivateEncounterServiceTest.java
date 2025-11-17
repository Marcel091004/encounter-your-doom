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
}

