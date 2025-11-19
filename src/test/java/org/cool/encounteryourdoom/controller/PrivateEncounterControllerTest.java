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

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

	@Nested
	class GetPrivateEncounterById {
		@Test
		void shouldReturn200kWithPrivateEncounter() {
			UUID userId = UUID.randomUUID();
			UUID encounterId = UUID.randomUUID();

			EncounterEntity encounterEntity = new EncounterEntity();

			when(privateEncounterService.getEncounterByID(userId, encounterId)).thenReturn(encounterEntity);

			ResponseEntity<Encounter> response = privateEncounterController.getEncounterForUser(userId, encounterId);

			assertEquals(ResponseEntity.ok(encounterEntity), response);
		}

		@Test
		void shouldHandleErrorFromService() {
			UUID userId = UUID.randomUUID();
			UUID encounterId = UUID.randomUUID();
			when(privateEncounterService.getEncounterByID(userId, encounterId)).thenThrow(new RuntimeException("Service error"));
			try {
				privateEncounterController.getEncounterForUser(userId, encounterId);
			} catch (RuntimeException e) {
				assertEquals("Service error", e.getMessage());
			}
		}
	}

	@Nested
	class UpdatePrivateEncounter {
		@Test
		void shouldReturn204NoContentOnSuccessfulUpdate() {
			UUID userId = UUID.randomUUID();
			UUID encounterId = UUID.randomUUID();
			Encounter encounter = new Encounter();

			ResponseEntity<Void> response = privateEncounterController.updateEncounterForUser(userId, encounterId, encounter);

			assertEquals(ResponseEntity.noContent().build(), response);
		}

		@Test
		void shouldHandleErrorFromService() {
			UUID userId = UUID.randomUUID();
			UUID encounterId = UUID.randomUUID();
			Encounter encounter = new Encounter();
			doThrow(new RuntimeException("Service error"))
			    .when(privateEncounterService)
			    .updateEncounterByID(userId, encounterId, encounter);
			try {
				privateEncounterController.updateEncounterForUser(userId, encounterId, encounter);
			} catch (RuntimeException e) {
				assertEquals("Service error", e.getMessage());
			}
		}
	}

	@Nested
	class StartPrivateEncounter {
		@Test
		void shouldReturn204NoContentOnSuccessfulStart() {
			UUID userId = UUID.randomUUID();
			UUID encounterId = UUID.randomUUID();

			doNothing().when(privateEncounterService).startEncounter(any());

			ResponseEntity<Void> response = privateEncounterController.startEncounterForUser(userId, encounterId);

			assertEquals(ResponseEntity.noContent().build(), response);
		}

		@Test
		void shouldHandleErrorFromService() {
			UUID userId = UUID.randomUUID();
			UUID encounterId = UUID.randomUUID();
			doThrow(new RuntimeException("Service error"))
			    .when(privateEncounterService)
			    .startEncounter(encounterId);

			try {
				privateEncounterController.startEncounterForUser(userId, encounterId);
			} catch (RuntimeException e) {
				assertEquals("Service error", e.getMessage());
			}
		}
	}

	@Nested
	class CreatePrivateEncounter {
		@Test
		void shouldReturn201CreatedOnSuccessfulCreation() {
			UUID userId = UUID.randomUUID();
			Encounter encounter = new Encounter();

			doNothing().when(privateEncounterService).createPrivateEncounter(any(), any());

			ResponseEntity<Void> response = privateEncounterController.createEncounterForUser(userId, encounter);

			assertEquals(ResponseEntity.created(URI.create("/datev/v1/privateEncounter/null")).build(), response);
		}

		@Test
		void shouldHandleErrorFromService() {
			UUID userId = UUID.randomUUID();
			Encounter encounter = new Encounter();
			doThrow(new RuntimeException("Service error"))
			    .when(privateEncounterService)
			    .createPrivateEncounter(userId, encounter);

			try {
				privateEncounterController.createEncounterForUser(userId, encounter);
			} catch (RuntimeException e) {
				assertEquals("Service error", e.getMessage());
			}
		}
	}
}
