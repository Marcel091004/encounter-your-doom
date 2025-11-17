package org.cool.encounteryourdoom.API;

import org.cool.encounteryourdoom.controller.PrivateEncounterController;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.Encounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PrivateEncounterApiTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private PrivateEncounterController privateEncounterController;

	@Nested
	class GetPrivateEncounters {
		@Test
		void getsPrivateEncountersWith200Ok() throws Exception {

			Encounter privateEncounter = new Encounter();
			when(privateEncounterController.getEncounterForUser(any(), any())).thenReturn(ResponseEntity.ok(privateEncounter));

			String userid = "123e4567-e89b-12d3-a456-426614174000";

			mockMvc.perform(get("/datev/v1/privateEncounter/" + userid))
					.andExpect(status().isOk());
		}

		@Test
		void returns400BadRequestWhenGivenInvalidUUID() throws Exception {

			when(privateEncounterController.getEncounterForUser(any(), any())).thenReturn(ResponseEntity.badRequest().build());

			String userid = "invalid-uuid";

			mockMvc.perform(get("/datev/v1/privateEncounter/" + userid))
					.andExpect(status().isBadRequest());
		}
	}
}
