package org.cool.encounteryourdoom.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.ActiveEncounter;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class ActiveEncounterServiceTest {

	private ActiveEncounter activeEncounter;
	private ActiveEncounterService activeEncounterService;

	@BeforeEach
	void setUp() {
		activeEncounter = mock(ActiveEncounter.class);
		activeEncounterService = mock(ActiveEncounterService.class);
	}

	//TODO finish these tests

//	@Nested
//	class GetActiveEncounter {
//
//		@Test
//		void shouldReturnActiveEncounter() {
//
//			ActiveEncounter encounter = new ActiveEncounter();
//
//			when(activeEncounterService.getActiveEncounter(null)).thenReturn(activeEncounter);
//
//			ActiveEncounter response = activeEncounterService.getActiveEncounter(null);
//
//			assertEquals(encounter.getEncounter(), response.getEncounter());
//		}
//
//		@Test
//		void getActiveEncounterWith400() {
//			when(activeEncounterService.getActiveEncounter(null)).thenReturn(null);
//
//			ActiveEncounter response = activeEncounterService.getActiveEncounter(null);
//
//            assertNull(response);
//		}
//	}
//
//    @Nested
//    class createActiveEncounterForUser {
//
//        @Test
//        void getActiveEncounterWith200() {
//
//            ActiveEncounter encounter = new ActiveEncounter();
//
//            doNothing().when(activeEncounterService).createActiveEncounterForUser(any(), any());
//
//
//            ActiveEncounter response = activeEncounterService.getActiveEncounter(null);
//
//            assertEquals(encounter.getEncounter(), response.getEncounter());
//        }
//
//        @Test
//        void getActiveEncounterWith400() {
//            when(activeEncounterService.getActiveEncounter(null)).thenReturn(null);
//
//            ActiveEncounter response = activeEncounterService.getActiveEncounter(null);
//
//            assertNull(response);
//        }
//    }
}
