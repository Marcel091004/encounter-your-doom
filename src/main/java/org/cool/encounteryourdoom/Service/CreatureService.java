package org.cool.encounteryourdoom.Service;

import org.cool.encounteryourdoom.Repository.CreatureRepository;
import org.cool.encounteryourdoom.model.CreatureEntity;
import org.openapitools.model.Creature;
import org.openapitools.model.Rarity;
import org.openapitools.model.Region;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CreatureService {

    //TODO : Implement CreatureService

    private final CreatureRepository creatureRepository;

    CreatureService(CreatureRepository creatureRepository) {
        this.creatureRepository = creatureRepository;
    }

    public List<CreatureEntity> getAllCreatures() {

            return this.creatureRepository.findAll();
    }

    public List<CreatureEntity> getAllCreatures(Region region) {

        if ()
            return this.creatureRepository.findAll();
    }

    public List<CreatureEntity> getAllCreatures(Region region, Rarity rarity, String CR) {

        if ()
            return this.creatureRepository.findAll();
    }

    public List<CreatureEntity> getAllCreatures(Region region, Rarity rarity, String CR) {

        if ()
        return this.creatureRepository.findAll();
    }

}
