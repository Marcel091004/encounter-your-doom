package org.cool.encounteryourdoom.API;

import org.cool.encounteryourdoom.TestDataHelper;
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

import java.net.URI;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

	@Nested
	class GetPrivateEncounterById {
		@Test
		void getsPrivateEncounterByIdWith200Ok() throws Exception {

			Encounter privateEncounter = new Encounter();
			when(privateEncounterController.getEncounterForUser(any(), any())).thenReturn(ResponseEntity.ok(privateEncounter));

			UUID userid = UUID.randomUUID();
			UUID encounterid = UUID.randomUUID();

			mockMvc.perform(get("/datev/v1/privateEncounter/" + encounterid + "/user/" + userid))
					.andExpect(status().isOk());
		}

		@Test
		void returns400BadRequestWhenGivenInvalidUUID() throws Exception {

			when(privateEncounterController.getEncounterForUser(any(), any())).thenReturn(ResponseEntity.badRequest().build());

			String userid = "apfel";
			String encounterid = "apfelbaum";

			mockMvc.perform(get("/datev/v1/privateEncounter/" + encounterid + "/user/" + userid))
					.andExpect(status().isBadRequest());
		}
	}

	@Nested
	class UpdatePrivateEncounter {
		@Test
		void updatesPrivateEncounterWith204NoContent() throws Exception {

			when(privateEncounterController.updateEncounterForUser(any(), any(), any())).thenReturn(ResponseEntity.noContent().build());

			UUID userid = UUID.randomUUID();
			UUID encounterid = UUID.randomUUID();
			String content = TestDataHelper.getEncounterJson();

			mockMvc.perform(put("/datev/v1/privateEncounter/" + encounterid + "/user/" + userid)
							.contentType("application/json")
							.content(content)
					)
					.andExpect(status().isNoContent());
		}

		@Test
		void returns400BadRequestWhenGivenInvalidUUID() throws Exception {

			when(privateEncounterController.updateEncounterForUser(any(), any(), any())).thenReturn(ResponseEntity.badRequest().build());

			String userid = "invalid-uuid";
			String encounterid = "invalid-uuid";
			String content = TestDataHelper.getEncounterJson();

			mockMvc.perform(put("/datev/v1/privateEncounter/" + encounterid + "/user/" + userid)
							.contentType("application/json")
							.content(content)
					)
					.andExpect(status().isBadRequest());
		}
	}

	@Nested
	class StartPrivateEncounter {
		@Test
		void startsPrivateEncounterWith204NoContent() throws Exception {

			when(privateEncounterController.startEncounterForUser(any(), any())).thenReturn(ResponseEntity.noContent().build());

			UUID userid = UUID.randomUUID();
			UUID encounterid = UUID.randomUUID();

			mockMvc.perform(post("/datev/v1/privateEncounter/" + encounterid + "/user/" + userid))
					.andExpect(status().isNoContent());
		}

		@Test
		void returns400BadRequestWhenGivenInvalidUUID() throws Exception {

			when(privateEncounterController.startEncounterForUser(any(), any())).thenReturn(ResponseEntity.badRequest().build());

			String userid = "invalid-uuid";
			String encounterid = "invalid-uuid";

			mockMvc.perform(post("/datev/v1/privateEncounter/" + encounterid + "/user/" + userid))
					.andExpect(status().isBadRequest());
		}
	}

	@Nested
	class CreatePrivateEncounter {
		@Test
		void createsPrivateEncounterWith201Created() throws Exception {

			when(privateEncounterController.createEncounterForUser(any(), any())).thenReturn(ResponseEntity.created(new URI("Test")).build());

			UUID userid = UUID.randomUUID();
			String content = TestDataHelper.getEncounterJson();

			mockMvc.perform(post("/datev/v1/privateEncounter/" + userid)
							.contentType("application/json")
							.content(content)
					)
					.andExpect(status().isCreated());
		}

		@Test
		void returns400BadRequestWhenGivenInvalidUUID() throws Exception {

			when(privateEncounterController.createEncounterForUser(any(), any())).thenReturn(ResponseEntity.badRequest().build());

			String userid = "invalid-uuid";
			String content = TestDataHelper.getEncounterJson();

			mockMvc.perform(post("/datev/v1/privateEncounter/" + userid)
							.contentType("application/json")
							.content(content)
					)
					.andExpect(status().isBadRequest());
		}
	}
}
