package org.cool.encounteryourdoom.model;

import org.cool.encounteryourdoom.MongoDBTestContainer;
import org.cool.encounteryourdoom.Repository.CreatureRepository;
import org.junit.jupiter.api.Test;
import org.openapitools.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
@Testcontainers
@ImportTestcontainers(MongoDBTestContainer.class)
class CreatureEntityTest {

    @Autowired
    private CreatureRepository creatureRepository;


    @Test
    void testSaveAndFindCreature() {
        CreatureEntity creature = new CreatureEntity();
        creature.setId(UUID.randomUUID());
        creature.setName("DemonicTurtle");
        creature.setAC(15);
        creature.setCR("14");
        creature.setHP(120);
        creature.setImmunities(List.of(DamageTypes.COLD));
        creature.setResistances(List.of(DamageTypes.FIRE));
        creature.setWeaknesses(List.of(DamageTypes.RADIANT));
        creature.setSpeed(60);
        creature.setRegion(Collections.singletonList(Region.FOREST));
        creature.setInitiative(24);
        creature.setRarity(Collections.singletonList(Rarity.RARE));
        creature.setStatusEffects(List.of(StatusEffects.UNCONSCIOUS));
        StatBlock statBlock = new StatBlock();
        statBlock.setStr(18);
        statBlock.setDex(12);
        statBlock.setCon(16);
        statBlock.setInt(10);
        statBlock.setWis(14);
        statBlock.setCha(8);
        creature.setStatBlock(statBlock);

        creatureRepository.save(creature);

        CreatureEntity found = creatureRepository.findById(creature.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("DemonicTurtle", found.getName());
        assertEquals(15, found.getAC());
        assertEquals("14", found.getCR());
        assertEquals(120, found.getHP());
        assertEquals(List.of(DamageTypes.COLD), found.getImmunities());
        assertEquals(List.of(DamageTypes.FIRE), found.getResistances());
        assertEquals(List.of(DamageTypes.RADIANT), found.getWeaknesses());
        assertEquals(60, found.getSpeed());
        assertEquals(Collections.singletonList(Region.FOREST), found.getRegion());
        assertEquals(24, found.getInitiative());
        assertEquals(Collections.singletonList(Rarity.RARE), found.getRarity());
        assertEquals(List.of(StatusEffects.UNCONSCIOUS), found.getStatusEffects());
        assertEquals(18, found.getStatBlock().getStr());
        assertEquals(12, found.getStatBlock().getDex());
        assertEquals(16, found.getStatBlock().getCon());
        assertEquals(10, found.getStatBlock().getInt());
        assertEquals(14, found.getStatBlock().getWis());
        assertEquals(8, found.getStatBlock().getCha());

    }

}