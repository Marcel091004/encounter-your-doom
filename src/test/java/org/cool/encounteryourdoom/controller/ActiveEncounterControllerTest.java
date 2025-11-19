package org.cool.encounteryourdoom.controller;

import org.cool.encounteryourdoom.service.ActiveEncounterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.ActiveEncounter;
import org.openapitools.model.Creature;
import org.openapitools.model.Rarity;
import org.openapitools.model.Region;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ActiveEncounterControllerTest {

    @Mock
    ActiveEncounterService activeEncounterService;

    private ActiveEncounterController activeEncounterController;

    @BeforeEach
    void setUp() {
        activeEncounterService = mock(ActiveEncounterService.class);
        activeEncounterController = new ActiveEncounterController(activeEncounterService);
    }

    @Nested
    class GetActiveEncounterForUser {

        @Test
        void shouldReturn200OkWhenEverythingIsInOrder() {

            ActiveEncounter activeEncounter = new ActiveEncounter();
            when(activeEncounterService.getActiveEncounter(any())).thenReturn(activeEncounter);

            UUID uuid = UUID.randomUUID();

            ResponseEntity<ActiveEncounter> response = activeEncounterController.getActiveEncounterForUser(uuid);

            assertEquals(response, ResponseEntity.ok(activeEncounter));

        }

        @Test
        void shouldHandleThrowableFromService404() {
            when(activeEncounterService.getActiveEncounter(any())).thenThrow(new RuntimeException("Service error"));

            try {
                activeEncounterController.getActiveEncounterForUser(null);
            } catch (RuntimeException e) {
                assertThat(e.getMessage()).isEqualTo("Service error");
            }
        }
    }

    @Nested
    class UpdateCreatureInActiveEncounterForUser {

        @Test
        void shouldReturn204NoContentWhenEverythingIsInOrder() {

            UUID userId = UUID.randomUUID();
            UUID creatureId = UUID.randomUUID();

            doNothing().when(activeEncounterService).updateCreatureInActiveEncounter(any(),any(),any(),any(),any()) ;

                    ResponseEntity<Void> response = activeEncounterController.updateCreatureInActiveEncounterForUser(
                    userId,
                    creatureId,
                    10,
                    5,
                    new ArrayList<>()
            );

            assertEquals(response, ResponseEntity.noContent().build());

        }

        @Test
        void shouldHandleThrowableFromService404() {

            doThrow(new RuntimeException("Service error")).when(activeEncounterService).updateCreatureInActiveEncounter(any(),any(),any(),any(),any());

            try {
                activeEncounterController.updateCreatureInActiveEncounterForUser(any(),any(),any(),any(),any());
            } catch (RuntimeException e) {
                assertThat(e.getMessage()).isEqualTo("Service error");
            }
        }
    }

    @Nested
    class CloseActiveEncounterForUser {

        @Test
        void shouldReturn204NoContentWhenEverythingIsInOrder() {

            UUID userId = UUID.randomUUID();

            doNothing().when(activeEncounterService).deleteActiveEncounter(any());

            ResponseEntity<Void> response = activeEncounterController.closeActiveEncounterForUser(userId);

            assertEquals(response, ResponseEntity.noContent().build());

        }

        @Test
        void shouldHandleThrowableFromService404() {

            doThrow(new RuntimeException("Service error")).when(activeEncounterService).deleteActiveEncounter(any());

            try {
                activeEncounterController.closeActiveEncounterForUser(any());
            } catch (RuntimeException e) {
                assertThat(e.getMessage()).isEqualTo("Service error");
            }
        }
    }

}
