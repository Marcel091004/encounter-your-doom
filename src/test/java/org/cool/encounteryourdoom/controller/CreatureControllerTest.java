package org.cool.encounteryourdoom.controller;

import org.cool.encounteryourdoom.service.CreatureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.Creature;
import org.openapitools.model.Rarity;
import org.openapitools.model.Region;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreatureControllerTest {

	@Mock
	CreatureService creatureService;

	private CreatureController creatureController;

	@BeforeEach
	void setUp() {
		creatureService = mock(CreatureService.class);
		creatureController = new CreatureController(creatureService);
	}

	@Nested
	class GetAllCreatures {
		@Test
		void shouldReturn200OKWhenEverythingIsInOrder() {
			Region region = Region.FOREST;
			Rarity rarity = Rarity.COMMON;
			String CR = "1";

			List<Creature> creatures = new ArrayList<>();
			creatures.add(new Creature());

			when(creatureService.getAllCreatures(any())).thenReturn(creatures);

			ResponseEntity<List<Creature>> response = creatureController.getCreatures(region, rarity, CR);

			assertEquals(response, ResponseEntity.ok(creatures));
		}

		@Test
		void shouldHandleNullParameters() {
			List<Creature> creatures = new ArrayList<>();
			creatures.add(new Creature());

			when(creatureService.getAllCreatures(any())).thenReturn(creatures);

			ResponseEntity<List<Creature>> response = creatureController.getCreatures(null, null, null);

			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
			assertEquals(response, ResponseEntity.ok(creatures));
		}

		@Test
		void shouldHandleThrowableFromService() {
			when(creatureService.getAllCreatures(any())).thenThrow(new RuntimeException("Service error"));

			try {
				creatureController.getCreatures(null, null, null);
			} catch (RuntimeException e) {
				assertThat(e.getMessage()).isEqualTo("Service error");
			}
		}
	}

	@Nested
	class GetCreatureById {
		@Test
		void shouldReturn200OKWhenCreatureIsFound() {
			java.util.UUID id = java.util.UUID.randomUUID();
			Creature creature = new Creature();

			when(creatureService.getCreatureByID(id)).thenReturn(creature);

			ResponseEntity<Creature> response = creatureController.getCreatureById(id);

			assertEquals(response, ResponseEntity.ok(creature));
		}

		@Test
		void shouldHandle400NotFound() {
			java.util.UUID id = java.util.UUID.randomUUID();

			when(creatureService.getCreatureByID(id)).thenThrow(new RuntimeException("Creature not found"));

			try {
				creatureController.getCreatureById(id);
			} catch (RuntimeException e) {
				assertThat(e.getMessage()).isEqualTo("Creature not found");
			}
		}
	}

	@Nested
	class UpdateCreatureById {
		@Test
		void shouldReturn204NoContentWhenUpdateIsSuccessful() {
			java.util.UUID id = java.util.UUID.randomUUID();
			Creature creature = new Creature();

			ResponseEntity<Void> response = creatureController.updateCreatureById(id, creature);

			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
		}

		@Test
		void shouldThrowExceptionWhenCreatureNotFound() {
			java.util.UUID id = java.util.UUID.randomUUID();
			Creature creature = new Creature();

			try {
				creatureController.updateCreatureById(id, creature);
			} catch (RuntimeException e) {
				assertThat(e.getMessage()).isEqualTo("Update failed");
			}
		}
	}

	@Nested
	class CreateCreature {
		@Test
		void shouldReturn201CreatedWhenCreatureIsCreated() {
			Creature creature = new Creature();
			java.util.UUID generatedId = java.util.UUID.randomUUID();

			when(creatureService.createCreature(creature)).thenReturn(generatedId);

			ResponseEntity<Void> response = creatureController.createCreature(creature);

			assertThat(response.getHeaders().getLocation().toString()).isEqualTo("/datev/v1/creature/" + generatedId);
		}

		@Test
		void shouldHandle400BadRequestWhenWrongDataProvided() {
			Creature creature = new Creature();

			when(creatureService.createCreature(creature)).thenThrow(new RuntimeException("Invalid data"));

			try {
				creatureController.createCreature(creature);
			} catch (RuntimeException e) {
				assertThat(e.getMessage()).isEqualTo("Invalid data");
			}
		}
	}

	@Nested
	class GetRandomCreature {
		@Test
		void shouldReturn200OKWhenRandomCreatureIsFound() {
			Region region = Region.DESERT;
			Rarity rarity = Rarity.RARE;
			String CR = "5";

			Creature creature = new Creature();

			when(creatureService.getRandomCreature(any())).thenReturn(creature);

			ResponseEntity<Creature> response = creatureController.getRandomCreature(region, rarity, CR);

			assertEquals(response, ResponseEntity.ok(creature));
		}

		@Test
		void shouldHandleNoCreatureFound() {
			Region region = Region.DESERT;
			Rarity rarity = Rarity.RARE;
			String CR = "10";

			when(creatureService.getRandomCreature(any())).thenThrow(new RuntimeException("No creature found"));

			try {
				creatureController.getRandomCreature(region, rarity, CR);
			} catch (RuntimeException e) {
				assertThat(e.getMessage()).isEqualTo("No creature found");
			}
		}
	}
}
