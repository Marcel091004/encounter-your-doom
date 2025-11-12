package org.cool.encounteryourdoom.API;

import org.cool.encounteryourdoom.Controller.EncounterController;
import org.cool.encounteryourdoom.Service.EncounterService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.openapitools.model.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EncounterApiTest {

	@MockitoBean
	EncounterController EncounterController;

	@Autowired
	private MockMvc mockMvc;

	@Nested
	class MoveEncounter {

		@Test
		void shouldReturn200OKWhenEverythingIsInOrder() throws Exception {

			UUID id = UUID.randomUUID();
			UUID userid = UUID.randomUUID();

			mockMvc.perform(put("/datev/v1/encounter/" + id + "/user/" + userid + "/move"))
					.andExpect(status().isOk())
					.andExpect(content().string(""));
		}

		@Test
		void shouldReturn400BadRequestWhenGivenWrongId() throws Exception {
			String id = "Apfel";
			UUID userid = UUID.randomUUID();

			mockMvc.perform(put("/datev/v1/encounter/" + id + "/user/" + userid + "/move"))
					.andExpect(status().isBadRequest()).andDo(print())
					.andExpect(content().string("{\"details\":\"Invalid UUID string: Apfel\",\"message\":\"JSON parse error\"}"));
		}

		@Test
		void shouldReturn405MethodNotAllowed() throws Exception {
			UUID id = UUID.randomUUID();
			UUID userid = UUID.randomUUID();

			mockMvc.perform(post("/datev/v1/encounter/" + id + "/user/" + userid + "/move"))
					.andExpect(status().isMethodNotAllowed());
		}
	}

	@Nested
	class GetAllEncounters {
		@Test
		void shouldReturn200OKWhenGettingAllEncounters() throws Exception {
			mockMvc.perform(get("/datev/v1/encounter"))
					.andExpect(status().isOk());
		}

		@Test
		void shouldReturn405MethodNotAllowedWhenUsingPut() throws Exception {
			mockMvc.perform(put("/datev/v1/encounter"))
					.andExpect(status().isMethodNotAllowed());
		}

		@Test
		void shouldReturn200OKWhenGettingEncountersWithFilters() throws Exception {
			mockMvc.perform(get("/datev/v1/encounter")
							.param("Region", Region.FOREST.toString())
							.param("Rarity", "common"))
					.andExpect(status().isOk());
		}

		//TODO: Falsch implementierter Code, da ungültige Filterwerte nicht zu 400 führen
//		@Test
//		void shouldReturn400BadRequestWhenUsingInvalidFilter() throws Exception {
//			mockMvc.perform(get("/datev/v1/encounter")
//							.param("Region", "invalidCR"))
//					.andExpect(status().isBadRequest());
//		}
	}

	@Nested
	class GetEncounterById {

		@Test
		void shouldReturn200OKWhenGettingEncounterById() throws Exception {
			UUID id = UUID.randomUUID();
			mockMvc.perform(get("/datev/v1/encounter/" + id))
					.andExpect(status().isOk());
		}

		@Test
		void shouldReturn405MethodNotAllowedWhenUsingDelete() throws Exception {
			UUID id = UUID.randomUUID();
			mockMvc.perform(delete("/datev/v1/encounter/" + id))
					.andExpect(status().isMethodNotAllowed());
		}

		@Test
		void shouldReturn400BadRequestWhenEncounterDoesNotExist() throws Exception {
			String id = "Apfel";
			mockMvc.perform(get("/datev/v1/encounter/" + id))
					.andExpect(status().isBadRequest());
		}
	}

	@Nested
	class UpdateEncounterById {
		@Test
		void shouldReturn200OKWhenUpdatingEncounterById() throws Exception {
//ToDO: anderes Mocking mit when einbauen (ABER NUR CONTROLLER, Service nicht erwähnen)
// Wie in Marcels Beispiel
// ACHTUNG: Es müssen bei Bedarf Interfaces gemockt werden, keine konkreten Klassen!
			UUID id = UUID.randomUUID();
			String encounterJson = "{\"name\":\"Test Encounter\",\"description\":\"Testfall\",\"difficultyLevel\":\"Hard\"}";
			mockMvc.perform(put("/datev/v1/encounter/" + id)
							.contentType(MediaType.APPLICATION_JSON)
							.content(encounterJson))
					.andExpect(status().isOk());
		}

		@Test
		void shouldReturn400BadRequestWhenUpdatingWithInvalidId() throws Exception {
			String id = "Apfel";
			String encounterJson = "{\"name\":\"Test Encounter\",\"description\":\"Test\",\"difficultyLevel\":null}";
			mockMvc.perform(put("/datev/v1/encounter/" + id)
							.contentType("application/json")
							.content(encounterJson))
					.andExpect(status().isBadRequest());
		}

		@Test
		void shouldReturn415UnsupportedMediaTypeWhenNoContentType() throws Exception {
			UUID id = UUID.randomUUID();
			String encounterJson = "{\"name\":\"Test Encounter\",\"description\":\"Test\"}";
			mockMvc.perform(put("/datev/v1/encounter/" + id)
							.content(encounterJson))
					.andExpect(status().isUnsupportedMediaType());
		}

		@Test
		void shouldReturn405MethodNotAllowedWhenUsingDelete() throws Exception {
			UUID id = UUID.randomUUID();
			mockMvc.perform(delete("/datev/v1/encounter/" + id))
					.andExpect(status().isMethodNotAllowed());
		}
	}
}
