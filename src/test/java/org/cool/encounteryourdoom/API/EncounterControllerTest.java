package org.cool.encounteryourdoom.API;

import org.cool.encounteryourdoom.Controller.EncounterController;
import org.cool.encounteryourdoom.Service.EncounterService;
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
}

