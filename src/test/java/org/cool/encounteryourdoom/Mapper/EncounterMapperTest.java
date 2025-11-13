package org.cool.encounteryourdoom.Mapper;

import org.cool.encounteryourdoom.model.EncounterEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.openapitools.model.Encounter;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EncounterMapperTest {
    private EncounterMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(EncounterMapper.class);
    }

    @Test
    void toEncounterEntity_shouldMapFieldsCorrectly() {
        Encounter encounter = new Encounter();
        encounter.setName("Test Encounter");
        // weitere Felder setzen, falls vorhanden

        EncounterEntity entity = mapper.toEncounterEntity(encounter);
        assertNotNull(entity);
        assertEquals("Test Encounter", entity.getName());
        // weitere Feldprüfungen
    }

    @Test
    void toEncounter_shouldMapFieldsCorrectly() {
        EncounterEntity entity = new EncounterEntity();
        entity.setName("Test Entity");
        // weitere Felder setzen, falls vorhanden

        Encounter encounter = mapper.toEncounter(entity);
        assertNotNull(encounter);
        assertEquals("Test Entity", encounter.getName());
        // weitere Feldprüfungen
    }

    @Test
    void toEncounterEntityList_shouldMapListCorrectly() {
        Encounter e1 = new Encounter();
        e1.setName("A");
        Encounter e2 = new Encounter();
        e2.setName("B");
        List<Encounter> encounters = Arrays.asList(e1, e2);

        List<EncounterEntity> entities = mapper.toEncounterEntityList(encounters);
        assertEquals(2, entities.size());
        assertEquals("A", entities.get(0).getName());
        assertEquals("B", entities.get(1).getName());
    }

    @Test
    void toEncounterList_shouldMapListCorrectly() {
        EncounterEntity e1 = new EncounterEntity();
        e1.setName("X");
        EncounterEntity e2 = new EncounterEntity();
        e2.setName("Y");
        List<EncounterEntity> entities = Arrays.asList(e1, e2);

        List<Encounter> encounters = mapper.toEncounterList(entities);
        assertEquals(2, encounters.size());
        assertEquals("X", encounters.get(0).getName());
        assertEquals("Y", encounters.get(1).getName());
    }
}

