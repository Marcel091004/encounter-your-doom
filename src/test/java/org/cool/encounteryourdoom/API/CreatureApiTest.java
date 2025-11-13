package org.cool.encounteryourdoom.API;

import org.cool.encounteryourdoom.Controller.CreatureController;
import org.cool.encounteryourdoom.TestDataHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.openapitools.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MvcResult;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.cool.encounteryourdoom.TestDataHelper.getCreatureJson;
import static org.mockito.ArgumentMatchers.any;
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
    class getCreature {

        @Test
        void shouldReturn200OKWhenEverythingIsInOrder() throws Exception {

            Creature testCreature = new Creature()
                    .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"))
                    .name("Shadow Wolf")
                    .creatureDescription("A mystical wolf wreathed in shadows, prowling the dark forests.")
                    .cr("3")
                    .HP(45)
                    .AC(14)
                    .speed(50)
                    .initiative(2)
                    .statBlock(new StatBlock()
                            .str(2)
                            .dex(2)
                            .con(1)
                            ._int(0)
                            .wis(1)
                            .cha(-1))
                    .attack(Arrays.asList(
                            new Attack()
                                    .name("Bite")
                                    .description("A powerful bite attack")
                                    .attackBonus(5)
                                    .damage("2d6 + 2 piercing")
                                    .damageType(List.of(DamageTypes.PIERCING)),
                            new Attack()
                                    .name("Shadow Strike")
                                    .description("Strikes from the shadows")
                                    .attackBonus(4)
                                    .damage("1d8 + 2 necrotic")
                                    .damageType(List.of(DamageTypes.NECROTIC))))
                    .traits(Arrays.asList("Darkvision 60ft", "Shadow Stealth"))
                    //TODO Region shouldn´t be a List (Wald Kobold, Berg Kobold etc)
                    .region(List.of(Region.FOREST))
                    //TODO Rarity shouldn't be a List
                    .rarity(List.of(Rarity.UNCOMMON))
                    .resistances(List.of(DamageTypes.NECROTIC))
                    .immunities(Collections.emptyList())
                    .weaknesses(List.of(DamageTypes.RADIANT))
                    .statusEffects(Collections.emptyList());


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
    }

    @Nested
    class getCreaturesById {

        @Test
        void shouldReturn200OKWhenCreatureExists() throws Exception {

            UUID creatureId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

            Creature testCreature = new Creature()
                    .id(creatureId)
                    .name("Shadow Wolf")
                    .creatureDescription("A mystical wolf wreathed in shadows, prowling the dark forests.")
                    .cr("3")
                    .HP(45)
                    .AC(14)
                    .speed(50)
                    .initiative(2)
                    .statBlock(new StatBlock()
                            .str(2)
                            .dex(2)
                            .con(1)
                            ._int(0)
                            .wis(1)
                            .cha(-1))
                    .attack(Arrays.asList(
                            new Attack()
                                    .name("Bite")
                                    .description("A powerful bite attack")
                                    .attackBonus(5)
                                    .damage("2d6 + 2 piercing")
                                    .damageType(List.of(DamageTypes.PIERCING)),
                            new Attack()
                                    .name("Shadow Strike")
                                    .description("Strikes from the shadows")
                                    .attackBonus(4)
                                    .damage("1d8 + 2 necrotic")
                                    .damageType(List.of(DamageTypes.NECROTIC))))
                    .traits(Arrays.asList("Darkvision 60ft", "Shadow Stealth"))
                    //TODO Region shouldn´t be a List (Wald Kobold, Berg Kobold etc)
                    .region(List.of(Region.FOREST))
                    //TODO Rarity shouldn't be a List
                    .rarity(List.of(Rarity.UNCOMMON))
                    .resistances(List.of(DamageTypes.NECROTIC))
                    .immunities(Collections.emptyList())
                    .weaknesses(List.of(DamageTypes.RADIANT))
                    .statusEffects(Collections.emptyList());


            when(CreatureController.getCreatureById(creatureId)).thenReturn(ResponseEntity.ok(testCreature));

            mockMvc.perform(get("/datev/v1/creature/" + creatureId))
                    .andExpect(status().isOk()).andDo(print())
                    .andExpect(content().json(objectMapper.writeValueAsString(testCreature)));
        }

        @Test
        void shouldReturn200OkWithEmptyArray() throws Exception {

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
            Creature testCreature = new Creature()
                    .id(creatureId)
                    .name("Shadow Wolf")
                    .creatureDescription("A mystical wolf wreathed in shadows, prowling the dark forests.")
                    .cr("3")
                    .HP(45)
                    .AC(14)
                    .speed(50)
                    .initiative(2)
                    .statBlock(new StatBlock()
                            .str(2)
                            .dex(2)
                            .con(1)
                            ._int(0)
                            .wis(1)
                            .cha(-1))
                    .attack(Arrays.asList(
                            new Attack()
                                    .name("Bite")
                                    .description("A powerful bite attack")
                                    .attackBonus(5)
                                    .damage("2d6 + 2 piercing")
                                    .damageType(List.of(DamageTypes.PIERCING)),
                            new Attack()
                                    .name("Shadow Strike")
                                    .description("Strikes from the shadows")
                                    .attackBonus(4)
                                    .damage("1d8 + 2 necrotic")
                                    .damageType(List.of(DamageTypes.NECROTIC))))
                    .traits(Arrays.asList("Darkvision 60ft", "Shadow Stealth"))
                    //TODO Region shouldn´t be a List (Wald Kobold, Berg Kobold etc)
                    .region(List.of(Region.FOREST))
                    //TODO Rarity shouldn't be a List
                    .rarity(List.of(Rarity.UNCOMMON))
                    .resistances(List.of(DamageTypes.NECROTIC))
                    .immunities(Collections.emptyList())
                    .weaknesses(List.of(DamageTypes.RADIANT))
                    .statusEffects(Collections.emptyList());


            when(CreatureController.updateCreatureById(creatureId, testCreature)).thenReturn(ResponseEntity.noContent().build());

            mockMvc.perform(put("/datev/v1/creature/" + creatureId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(testCreature)))
                    .andExpect(status().isNoContent()).andDo(print());
        }

        @Test
        void shouldReturn415WhenWrongContentTypeIsSend() throws Exception {

            UUID creatureId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
            Creature testCreature = new Creature()
                    .id(creatureId)
                    .name("Shadow Wolf")
                    .creatureDescription("A mystical wolf wreathed in shadows, prowling the dark forests.")
                    .cr("3")
                    .HP(45)
                    .AC(14)
                    .speed(50)
                    .initiative(2)
                    .statBlock(new StatBlock()
                            .str(2)
                            .dex(2)
                            .con(1)
                            ._int(0)
                            .wis(1)
                            .cha(-1))
                    .attack(Arrays.asList(
                            new Attack()
                                    .name("Bite")
                                    .description("A powerful bite attack")
                                    .attackBonus(5)
                                    .damage("2d6 + 2 piercing")
                                    .damageType(List.of(DamageTypes.PIERCING)),
                            new Attack()
                                    .name("Shadow Strike")
                                    .description("Strikes from the shadows")
                                    .attackBonus(4)
                                    .damage("1d8 + 2 necrotic")
                                    .damageType(List.of(DamageTypes.NECROTIC))))
                    .traits(Arrays.asList("Darkvision 60ft", "Shadow Stealth"))
                    //TODO Region shouldn´t be a List (Wald Kobold, Berg Kobold etc)
                    .region(List.of(Region.FOREST))
                    //TODO Rarity shouldn't be a List
                    .rarity(List.of(Rarity.UNCOMMON))
                    .resistances(List.of(DamageTypes.NECROTIC))
                    .immunities(Collections.emptyList())
                    .weaknesses(List.of(DamageTypes.RADIANT))
                    .statusEffects(Collections.emptyList());


            when(CreatureController.updateCreatureById(creatureId, testCreature)).thenReturn(ResponseEntity.noContent().build());

            mockMvc.perform(put("/datev/v1/creature/" + creatureId)
                            .contentType(MediaType.APPLICATION_ATOM_XML)
                            .content(objectMapper.writeValueAsString(testCreature)))
                    .andExpect(status().isUnsupportedMediaType()).andDo(print());
        }

        @Test
        void shouldReturn415WhenNoBodyIsSend() throws Exception {

            UUID creatureId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

            mockMvc.perform(put("/datev/v1/creature/" + creatureId)
                            .contentType(MediaType.APPLICATION_ATOM_XML)
                            .content(""))
                    .andExpect(status().isUnsupportedMediaType()).andDo(print());
        }

        @Test
        void shouldReturn400WhenBodyIsWrong() throws Exception {

            UUID creatureId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
            Creature testCreature = new Creature()
                    .id(creatureId)
                    .name("Shadow Wolf")
                    .creatureDescription("A mystical wolf wreathed in shadows, prowling the dark forests.")
                    .cr("3")
                    .HP(50000000)
                    .AC(14)
                    .speed(50)
                    .initiative(2000)
                    .statBlock(new StatBlock())
                    .attack(Arrays.asList())
                    .traits(Arrays.asList("Darkvision 60ft", "Shadow Stealth"))
                    //TODO Region shouldn´t be a List (Wald Kobold, Berg Kobold etc)
                    .region(List.of(Region.FOREST))
                    //TODO Rarity shouldn't be a List
                    .rarity(List.of(Rarity.UNCOMMON))
                    .resistances(List.of(DamageTypes.NECROTIC))
                    .immunities(Collections.emptyList())
                    .weaknesses(List.of(DamageTypes.RADIANT))
                    .statusEffects(Collections.emptyList());

            mockMvc.perform(put("/datev/v1/creature/" + creatureId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(testCreature)))
                    .andExpect(status().isBadRequest()).andDo(print());
        }
    }

    @Nested
    class createCreature {

        @Test
        void shouldReturn201CreatedWhenEverythingIsInOrder() throws Exception {

            UUID creatureId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");


            URI location = URI.create("/datev/v1/creature/" + creatureId);

            when(CreatureController.createCreature(any(Creature.class))).thenReturn(ResponseEntity.created(location).build());

            MvcResult result = mockMvc.perform(post("/datev/v1/creature/" + creatureId)
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
    }
}