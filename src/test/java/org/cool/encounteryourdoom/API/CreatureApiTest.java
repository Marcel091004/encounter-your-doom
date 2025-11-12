package org.cool.encounteryourdoom.API;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.openapitools.api.CreatureApi;
import org.openapitools.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
public class CreatureApiTest {

    @MockitoBean
    private CreatureApi CreatureApi;

    @Autowired
    private MockMvc mockMvc;

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



            when(CreatureApi.getCreatures(null, null, null)).thenReturn(ResponseEntity.ok(List.of(testCreature)));

            mockMvc.perform(get("/datev/v1/creature"))
                    .andExpect(status().isOk()).andDo(print())
                    .andExpect(content().string(String.valueOf(List.of(testCreature))));
        }

    }

}
