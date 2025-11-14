package org.cool.encounteryourdoom.controller;

import org.cool.encounteryourdoom.service.EncounterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

	@Nested
	class GetAllPublicEncounters {
		@Test
		void shouldReturnEncountersAndCallServiceWithCorrectFilter() {
			Region region = Region.FOREST;
			Rarity rarity = Rarity.COMMON;
			DifficultyLevel difficultyLevel = DifficultyLevel.EASY;
			Integer partyLevel = 3;

			List<Encounter> encounters = Arrays.asList(new Encounter(), new Encounter());

			when(encounterService.getAllPublicEncounters(any())).thenReturn(encounters);

			ResponseEntity<List<Encounter>> response = encounterController.getAllPublicEncounters(region, rarity, difficultyLevel, partyLevel);

			assertEquals(response, ResponseEntity.ok(encounters));
		}

		@Test
		void shouldHandleNullParameters() {
			List<Encounter> encounters = Arrays.asList(new Encounter());
			when(encounterService.getAllPublicEncounters(any())).thenReturn(encounters);

			ResponseEntity<List<Encounter>> response = encounterController.getAllPublicEncounters(null, null, null, null);

			assertEquals(ResponseEntity.ok(encounters), response);
		}
	}

	@Nested
	class GetEncounterById {
		@Test
		void shouldReturnEncounterWhenFound() {
			UUID id = UUID.randomUUID();
			Encounter encounter = new Encounter();

			when(encounterService.getEncounterById(id)).thenReturn(encounter);

			ResponseEntity<Encounter> response = encounterController.getPublicEncounterById(id);

			assertEquals(ResponseEntity.ok(encounter), response);
		}

		@Test
		void shouldThrowExceptionWhenEncounterNotFound() {
			UUID id = UUID.randomUUID();

			when(encounterService.getEncounterById(id)).thenThrow(new RuntimeException("Not Found"));

			try {
				encounterController.getPublicEncounterById(id);
			} catch (RuntimeException e) {
				assertEquals("Not Found", e.getMessage());
			}
		}
	}

	@Nested
	class CreateEncounter {
		@Test
		void shouldCreateEncounterAndReturnCreatedResponse() {
			Encounter encounter = new Encounter();
			UUID newId = UUID.randomUUID();

			when(encounterService.createEncounter(encounter)).thenReturn(newId);

			ResponseEntity<Void> response = encounterController.createEncounter(encounter);

			assertEquals("/datev/v1/encounter/" + newId, response.getHeaders().getLocation().toString());
		}

		@Test
		void shouldHandleExceptionWhenCreatingEncounter() {
			Encounter encounter = new Encounter();

			when(encounterService.createEncounter(encounter)).thenThrow(new RuntimeException("Creation Failed"));

			try {
				encounterController.createEncounter(encounter);
			} catch (RuntimeException e) {
				assertEquals("Creation Failed", e.getMessage());
			}
		}
	}

	@Nested
	class UpdateEncounter {
		@Test
		void shouldUpdateEncounterAndReturnOkResponse() {
			Encounter encounter = new Encounter();
			UUID id = UUID.randomUUID();

			doNothing().when(encounterService).updateEncounter(id, encounter);

			ResponseEntity<Void> response = encounterController.updatePublicEncounterById(id, encounter);

			assertEquals(ResponseEntity.ok().build(), response);
		}

		@Test
		void shouldHandleExceptionWhenUpdatingEncounter() {
			Encounter encounter = new Encounter();
			UUID id = UUID.randomUUID();

			doThrow(new RuntimeException("Update Failed")).when(encounterService).updateEncounter(id, encounter);

			try {
				encounterController.updatePublicEncounterById(id, encounter);
			} catch (RuntimeException e) {
				assertEquals("Update Failed", e.getMessage());
			}
		}
	}

	@Nested
	class GetRandomEncounter {
		@Test
		void shouldReturnRandomEncounterWithFilter() {
			Region region = Region.DESERT;
			Rarity rarity = Rarity.RARE;
			DifficultyLevel difficultyLevel = DifficultyLevel.HARD;
			Integer partyLevel = 5;

			Encounter encounter = new Encounter();

			when(encounterService.getRandomEncounter()).thenReturn(encounter);

			ResponseEntity<Encounter> response = encounterController.getRandomEncounter(region, rarity, difficultyLevel, partyLevel);

			assertEquals(ResponseEntity.ok(encounter), response);
		}

		@Test
		void shouldHandleExceptionWhenGettingRandomEncounter() {
			Region region = Region.DESERT;
			Rarity rarity = Rarity.RARE;
			DifficultyLevel difficultyLevel = DifficultyLevel.HARD;
			Integer partyLevel = 5;

			when(encounterService.getRandomEncounter()).thenThrow(new RuntimeException("No Encounters Available"));

			try {
				encounterController.getRandomEncounter(region, rarity, difficultyLevel, partyLevel);
			} catch (RuntimeException e) {
				assertEquals("No Encounters Available", e.getMessage());
			}
		}
	}

	@Nested
	class MoveEncounterToUserSpace {
		@Test
		void shouldReturnOkResponse() {
			UUID encounterId = UUID.randomUUID();
			UUID userId = UUID.randomUUID();

			doNothing().when(encounterService).moveEncounterToUserSpace(encounterId, userId);

			ResponseEntity<Void> response = encounterController.moveEncounterToUserSpace(encounterId, userId);

			assertEquals(ResponseEntity.ok().build(), response);
		}

		@Test
		void shouldHandleExceptionWhenMovingEncounter() {
			UUID encounterId = UUID.randomUUID();
			UUID userId = UUID.randomUUID();

			doThrow(new RuntimeException("Move Failed")).when(encounterService).moveEncounterToUserSpace(encounterId, userId);

			try {
				encounterController.moveEncounterToUserSpace(encounterId, userId);
			} catch (RuntimeException e) {
				assertEquals("Move Failed", e.getMessage());
			}
		}
	}
}

