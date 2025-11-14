package org.cool.encounteryourdoom.API;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.cool.encounteryourdoom.controller.CreatureController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.openapitools.model.Creature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.cool.encounteryourdoom.TestDataHelper.getBrokenCreatureJson;
import static org.cool.encounteryourdoom.TestDataHelper.getCreatureJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CreatureApiTest {

    @MockitoBean
    private CreatureController CreatureController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    class getCreatures {

        @Test
        void shouldReturn200OKWhenEverythingIsInOrder() throws Exception {

            Creature testCreature = objectMapper.readValue(getCreatureJson(), Creature.class);

            when(CreatureController.getCreatures(null, null, null)).thenReturn(ResponseEntity.ok(List.of(testCreature)));

            mockMvc.perform(get("/datev/v1/creature"))
                    .andExpect(status().isOk()).andDo(print())
                    .andExpect(content().json(objectMapper.writeValueAsString(List.of(testCreature))));
        }

        @Test
        void shouldReturn200OKWhenEverythingIsInOrderButNoEntriesInTheDB() throws Exception {

            when(CreatureController.getCreatures(null, null, null)).thenReturn(ResponseEntity.ok(List.of()));

            mockMvc.perform(get("/datev/v1/creature"))
                    .andExpect(status().isOk()).andDo(print())
                    .andExpect(content().json(objectMapper.writeValueAsString(List.of())));
        }

        @Test
        void shouldReturn400BadRequestWhenAParameterIsWrong() throws Exception {

            when(CreatureController.getCreatures(null, null, "apfel")).thenReturn(ResponseEntity.badRequest().build());

            mockMvc.perform(get("/datev/v1/creature?cr=apfel"))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    class getCreaturesById {

        @Test
        void shouldReturn200OKWhenCreatureExists() throws Exception {

            UUID creatureId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

            Creature creature = objectMapper.readValue(getCreatureJson(), Creature.class);

            when(CreatureController.getCreatureById(creatureId)).thenReturn(ResponseEntity.ok(creature));

            mockMvc.perform(get("/datev/v1/creature/" + creatureId))
                    .andExpect(status().isOk()).andDo(print())
                    .andExpect(content().json(getCreatureJson()));
        }

        @Test
        void shouldReturn200OkWithEmptyArray() throws Exception {
            //TODO kein leeres Array sondern 404?
            UUID creatureId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

            when(CreatureController.getCreatureById(creatureId)).thenReturn(ResponseEntity.ok(null));

            mockMvc.perform(get("/datev/v1/creature/" + creatureId))
                    .andExpect(status().isOk()).andDo(print())
                    .andExpect(content().string(""));

        }

        @Test
        void shouldReturn400BadRequestWhenIDisBroken() throws Exception {

            mockMvc.perform(get("/datev/v1/creature/apfel"))
                    .andExpect(status().isBadRequest()).andDo(print());
        }
    }

    @Nested
    class updateCreatureById {

        @Test
        void shouldReturn204NoContentWhenEverythingIsInOrder() throws Exception {

            UUID creatureId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

            when(CreatureController.updateCreatureById(eq(creatureId), any(Creature.class))).thenReturn(ResponseEntity.noContent().build());

            mockMvc.perform(put("/datev/v1/creature/" + creatureId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(getCreatureJson()))
                    .andExpect(status().isNoContent()).andDo(print());
        }

        @Test
        void shouldReturn415WhenWrongContentTypeIsSend() throws Exception {

            UUID creatureId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

            mockMvc.perform(put("/datev/v1/creature/" + creatureId)
                            .contentType(MediaType.APPLICATION_ATOM_XML)
                            .content(getCreatureJson()))
                    .andExpect(status().isUnsupportedMediaType()).andDo(print());
        }

        @Test
        void shouldReturn415WhenNoBodyIsSend() throws Exception {

            UUID creatureId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

            mockMvc.perform(put("/datev/v1/creature/" + creatureId)).andExpect(status().isUnsupportedMediaType()).andDo(print());
        }

        @Test
        void shouldReturn400WhenBodyIsWrong() throws Exception {
            UUID creatureId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

            mockMvc.perform(put("/datev/v1/creature/" + creatureId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(getBrokenCreatureJson()))
                    .andExpect(status().isBadRequest()).andDo(print());
        }
    }

    @Nested
    class createCreature {

        @Test
        void shouldReturn201CreatedWhenEverythingIsInOrder() throws Exception {

            UUID creatureId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");


            URI location = URI.create("/datev/v1/creature/"+ creatureId);

            when(CreatureController.createCreature(any(Creature.class))).thenReturn(ResponseEntity.created(location).build());


            MvcResult result = mockMvc.perform(post("/datev/v1/creature")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(getCreatureJson()))
                    .andExpect(status().isCreated())
                    .andExpect(header().exists("Location")).andReturn();

            String locationHeader = result.getResponse().getHeader("Location");

            Assertions.assertNotNull(locationHeader);
            locationHeader = locationHeader.replace("\"", "");
            locationHeader = locationHeader.replace("/datev/v1/creature/", "");

            UUID userId = UUID.fromString(locationHeader);

            Assertions.assertNotNull(userId);
        }

        @Test
        void shouldReturn400BadRequestWhenCreatureIsBroken() throws Exception {

           mockMvc.perform(post("/datev/v1/creature")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(getBrokenCreatureJson()))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    class getRandomCreature {

        @Test
        void shouldReturn200OKWithARandomCreature() throws Exception {

            Creature testCreature = objectMapper.readValue(getCreatureJson(), Creature.class);

            when(CreatureController.getRandomCreature(null, null, null)).thenReturn(ResponseEntity.ok(testCreature));

            mockMvc.perform(get("/datev/v1/creature/random"))
                    .andExpect(status().isOk()).andDo(print())
                    .andExpect(content().json(getCreatureJson()));
        }

        @Test
        void shouldReturn400BadRequestWhenParameterIsWrong() throws Exception {

            when(CreatureController.getRandomCreature(null, null, "apfel")).thenReturn(ResponseEntity.badRequest().build());

            mockMvc.perform(get("/datev/v1/creature/random?cr=apfel"))
                    .andExpect(status().isBadRequest());
        }

    }
}
