package org.cool.encounteryourdoom.API;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.cool.encounteryourdoom.controller.ActiveEncounterController;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.openapitools.model.ActiveEncounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.cool.encounteryourdoom.TestDataHelper.getActiveEncounterJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ActiveEncounterApiTest {


    @MockitoBean
    private ActiveEncounterController activeEncounterController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    class GetActiveEncounterForUser {

        @Test
        void shouldReturn200OKWhenEverythingIsInOrder() throws Exception {

            ActiveEncounter testActiveEncounter = objectMapper.readValue(getActiveEncounterJson(), ActiveEncounter.class);

            when(activeEncounterController.getActiveEncounterForUser(any())).thenReturn(ResponseEntity.ok(testActiveEncounter));

            mockMvc.perform(get("/datev/v1/activeEncounter/" + "123e4567-e89b-12d3-a456-426614174000"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(testActiveEncounter)));
        }

        @Test
        void shouldReturn405() throws Exception {

            mockMvc.perform(post("/datev/v1/activeEncounter/" + "123e4567-e89b-12d3-a456-426614174000"))
                    .andExpect(status().isMethodNotAllowed());
        }

        @Test
        void shouldReturn400BadRequestWhenEverythingIdIsBroken() throws Exception {

            mockMvc.perform(get("/datev/v1/activeEncounter/" + "apfel"))
                    .andExpect(status().isBadRequest());
        }

    }

    @Nested
    class UpdateCreatureInActiveEncounterForUser {

        @Test
        void shouldReturn204NoContentWhenEverythingIsInOrder() throws Exception {

            when(activeEncounterController.updateCreatureInActiveEncounterForUser(any(), any(), any(), any(), any())).thenReturn(ResponseEntity.noContent().build());

            mockMvc.perform(put("/datev/v1/activeEncounter/" + "123e4567-e89b-12d3-a456-426614174000/" + "123e4567-e89b-12d3-a456-426614174001"))
                    .andExpect(status().isNoContent());
        }

        @Test
        void shouldReturn204NoContentWhenHealIsSetTo10() throws Exception {

            when(activeEncounterController.updateCreatureInActiveEncounterForUser(any(), any(), any(), any(), any())).thenReturn(ResponseEntity.noContent().build());

            mockMvc.perform(put("/datev/v1/activeEncounter/"
                            + "123e4567-e89b-12d3-a456-426614174000/"
                            + "123e4567-e89b-12d3-a456-426614174001"
                            + "?heal=10"))
                    .andExpect(status().isNoContent());
        }

        @Test
        void shouldReturn204NoContentWhenHealIsSetTo10AndDamageIsSetTo5() throws Exception {

            when(activeEncounterController.updateCreatureInActiveEncounterForUser(any(), any(), any(), any(), any())).thenReturn(ResponseEntity.noContent().build());

            mockMvc.perform(put("/datev/v1/activeEncounter/"
                            + "123e4567-e89b-12d3-a456-426614174000/"
                            + "123e4567-e89b-12d3-a456-426614174001"
                            + "?heal=10"
                            + "&damage=5"))
                    .andExpect(status().isNoContent());
        }

        @Test
        void shouldReturn405() throws Exception {

            mockMvc.perform(post("/datev/v1/activeEncounter/" + "123e4567-e89b-12d3-a456-426614174000/" + "123e4567-e89b-12d3-a456-426614174001"))
                    .andExpect(status().isMethodNotAllowed());
        }

        @Test
        void shouldReturn400BadRequestWhenEverythingIdIsBroken() throws Exception {

            mockMvc.perform(put("/datev/v1/activeEncounter/" + "apfel/banana"))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    class CloseActiveEncounterForUser {

        @Test
        void shouldReturn204NoContentWhenEverythingIsInOrder() throws Exception {

            when(activeEncounterController.closeActiveEncounterForUser(any())).thenReturn(ResponseEntity.noContent().build());

            mockMvc.perform(delete("/datev/v1/activeEncounter/" + "123e4567-e89b-12d3-a456-426614174000"))
                    .andExpect(status().isNoContent());
        }
    }
}
