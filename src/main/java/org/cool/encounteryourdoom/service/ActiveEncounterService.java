package org.cool.encounteryourdoom.service;

import org.cool.encounteryourdoom.repository.CreatureRepository;
import org.cool.encounteryourdoom.repository.EncounterRepository;
import org.openapitools.model.ActiveEncounter;
import org.openapitools.model.Creature;
import org.openapitools.model.Encounter;
import org.openapitools.model.StatusEffects;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class ActiveEncounterService {

     HashMap<UUID, ActiveEncounter> activeEncounters;

     private final EncounterRepository encounterRepository;
     private final CreatureRepository creatureRepository;

    public ActiveEncounterService(EncounterRepository encounterRepository, CreatureRepository creatureRepository) {
        this.encounterRepository = encounterRepository;
        this.creatureRepository = creatureRepository;
    }


    public ActiveEncounter getActiveEncounter(UUID userId) {
        return activeEncounters.get(userId);
    }

    public void createActiveEncounterForUser(UUID userId, UUID encounterId) {
        Encounter encounter = encounterRepository.findById(encounterId).get();
        ActiveEncounter activeEncounter = buildActiveEncounter(encounter);
        activeEncounters.put(userId, activeEncounter);
    }


    private ActiveEncounter buildActiveEncounter(Encounter encounter) {
        ActiveEncounter activeEncounter = new ActiveEncounter();

        List<UUID> UUIDOfCreatures = encounter.getCreatures();

        List<Creature> creatures = new ArrayList<>();

        UUIDOfCreatures.forEach(uuid -> {

           Creature creature = creatureRepository.findById(uuid).get();
              creatures.add(creature);

        });

        activeEncounter.setEncounter(encounter);
        activeEncounter.setCreatures(creatures);
        return activeEncounter;
    }

    public void updateCreatureInActiveEncounter(UUID userId, UUID creatureId, Integer heal, Integer damage, List<StatusEffects> statusEffects) {

        ActiveEncounter activeEncounter = activeEncounters.get(userId);
        List<Creature> creatures = activeEncounter.getCreatures();

        Creature creatureToBeUpdated = creatures.stream()
                .filter(creature -> creature.getId().equals(creatureId))
                .findFirst()
                .orElse(null);

        if (creatureToBeUpdated != null) {

            if (heal != null) {
                int newHealth = creatureToBeUpdated.getHP() + heal;
                creatureToBeUpdated.setHP(newHealth);
            }

            if (damage != null) {
                int newHealth = creatureToBeUpdated.getHP() - damage;
                creatureToBeUpdated.setHP(newHealth);
            }

            if (statusEffects != null) {
                creatureToBeUpdated.setStatusEffects(statusEffects);
            }
        } else {
            throw new IllegalArgumentException("Creature with ID " + creatureId + " not found in active encounter for user " + userId);
        }

    }

    public void deleteActiveEncounter(UUID userId) {
        activeEncounters.remove(userId);
    }

}
