package org.cool.encounteryourdoom.API;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EncounterApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    class MoveEncounter {

        @Test
        void shouldReturn200OKWhenEverythingIsInOrder() throws Exception {

            UUID id = UUID.randomUUID();
            UUID userid = UUID.randomUUID();

            mockMvc.perform(put("/datev/v1/encounter/"+ id +"/"+ userid +"/move"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(""));
        }

        @Test
        void shouldReturn400BadRequestWhenGivenWrongId() throws Exception {
            String id = "Apfel";
            UUID userid = UUID.randomUUID();

            mockMvc.perform(put("/datev/v1/encounter/"+ id +"/"+ userid +"/move"))
                    .andExpect(status().isBadRequest()).andDo(print())
                    .andExpect(content().string("{\"details\":\"Invalid UUID string: Apfel\",\"message\":\"JSON parse error\"}"));
        }

        @Test
        void shouldReturn405MethodNotAllowed() throws Exception {
            UUID id = UUID.randomUUID();
            UUID userid = UUID.randomUUID();

            mockMvc.perform(post("/datev/v1/encounter/"+ id +"/"+ userid +"/move"))
                    .andExpect(status().isMethodNotAllowed());
        }
    }
}
