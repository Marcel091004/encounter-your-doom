package org.cool.encounteryourdoom.controller;

import org.cool.encounteryourdoom.mapper.PrivateEncounterMapper;
import org.cool.encounteryourdoom.model.EncounterEntity;
import org.cool.encounteryourdoom.service.PrivateEncounterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.openapitools.model.DifficultyLevel;
import org.openapitools.model.Encounter;
import org.openapitools.model.Rarity;
import org.openapitools.model.Region;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PrivateEncounterControllerTest {
	private PrivateEncounterService privateEncounterService;
	private PrivateEncounterController privateEncounterController;
	private PrivateEncounterMapper privateEncounterMapper;

	@BeforeEach
	void setUp() {
		privateEncounterService = mock(PrivateEncounterService.class);
		privateEncounterMapper = mock(PrivateEncounterMapper.class);
		privateEncounterController = new PrivateEncounterController(privateEncounterService, privateEncounterMapper);
	}

	@Nested
	class GetPrivateEncounters {
		@Test
		void shouldReturn200kWithPrivateEncounters() {
			UUID userId = UUID.randomUUID();

			Region region = Region.FOREST;
			Rarity rarity = Rarity.COMMON;
			DifficultyLevel difficultyLevel = DifficultyLevel.EASY;
			Integer partyLevel = 3;

			List<EncounterEntity> encounters = Arrays.asList(new EncounterEntity(), new EncounterEntity());
			List<Encounter> mappedEncounters = Arrays.asList(new Encounter(), new Encounter());

			when(privateEncounterMapper.toEncounter(encounters)).thenReturn(mappedEncounters);
			when(privateEncounterService.getEncounterList(any())).thenReturn(encounters);

			ResponseEntity<List<Encounter>> resopnse = privateEncounterController.getAllEncountersForUser(userId, region, rarity, difficultyLevel, partyLevel);

			assertEquals(ResponseEntity.ok(mappedEncounters), resopnse);
		}

		@Test
		void shouldReturnEmptyListWhenNoEncountersFound() {
			UUID userId = UUID.randomUUID();

			Region region = Region.DESERT;
			Rarity rarity = Rarity.RARE;
			DifficultyLevel difficultyLevel = DifficultyLevel.HARD;
			Integer partyLevel = 5;

			List<EncounterEntity> encounters = Arrays.asList();
			List<Encounter> mappedEncounters = Arrays.asList();

			when(privateEncounterMapper.toEncounter(encounters)).thenReturn(mappedEncounters);
			when(privateEncounterService.getEncounterList(any())).thenReturn(encounters);

			ResponseEntity<List<Encounter>> response = privateEncounterController.getAllEncountersForUser(userId, region, rarity, difficultyLevel, partyLevel);

			assertEquals(ResponseEntity.ok(mappedEncounters), response);
		}

		@Test
		void shouldHandleNullFilters() {
			UUID userId = UUID.randomUUID();

			List<EncounterEntity> encounters = Arrays.asList(new EncounterEntity());
			List<Encounter> mappedEncounters = Arrays.asList(new Encounter());

			when(privateEncounterMapper.toEncounter(encounters)).thenReturn(mappedEncounters);
			when(privateEncounterService.getEncounterList(any())).thenReturn(encounters);

			ResponseEntity<List<Encounter>> response = privateEncounterController.getAllEncountersForUser(userId, null, null, null, null);

			assertEquals(ResponseEntity.ok(mappedEncounters), response);
		}

		@Test
		void shouldHandleErrorFromMapper() {
			//Dieser Test existiert um zu verhindern das jemand Error Handling Local löst und nicht den globalen Error Handler benutzt
			UUID userId = UUID.randomUUID();

			Region region = Region.FOREST;
			Rarity rarity = Rarity.RARE;
			DifficultyLevel difficultyLevel = DifficultyLevel.HARD;
			Integer partyLevel = 10;

			List<EncounterEntity> encounters = Arrays.asList(new EncounterEntity());

			when(privateEncounterService.getEncounterList(any())).thenReturn(encounters);
			when(privateEncounterMapper.toEncounter(encounters)).thenThrow(new RuntimeException("Mapping error"));

			try {
				privateEncounterController.getAllEncountersForUser(userId, region, rarity, difficultyLevel, partyLevel);
			} catch (RuntimeException e) {
				assertEquals("Mapping error", e.getMessage());
			}
		}

		@Test
		void shouldHandleErrorFromService() {
			//Dieser Test existiert um zu verhindern das jemand Error Handling Local löst und nicht den globalen Error Handler benutzt
			UUID userId = UUID.randomUUID();

			Region region = Region.DESERT;
			Rarity rarity = Rarity.VERY_RARE;
			DifficultyLevel difficultyLevel = DifficultyLevel.EASY;
			Integer partyLevel = 1;

			when(privateEncounterService.getEncounterList(any())).thenThrow(new RuntimeException("Service error"));

			try {
				privateEncounterController.getAllEncountersForUser(userId, region, rarity, difficultyLevel, partyLevel);
			} catch (RuntimeException e) {
				assertEquals("Service error", e.getMessage());
			}
		}
	}
}
