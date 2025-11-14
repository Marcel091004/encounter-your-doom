package org.cool.encounteryourdoom.API;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.api.PrivateEncounterApi;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PrivateEncounterApiTest {

	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
	    mockMvc = MockMvcBuilders.standaloneSetup(new PrivateEncounterApi() {
		}).build();
	}

	@Nested
	class GetPrivateEncounters {
		@Test
		void getsPrivateEncountersWith200Ok() throws Exception {
			String userid = "123e4567-e89b-12d3-a456-426614174000";

			mockMvc.perform(get("/datev/v1/privateEncounter/" + userid))
					.andExpect(status().isOk());
		}
		@Test
		void returns400BadRequestWhenGivenInvalidUUID() throws Exception {
			String userid = "invalid-uuid";

			mockMvc.perform(get("/datev/v1/privateEncounter/" + userid))
					.andExpect(status().isBadRequest());
		}
	}
}
