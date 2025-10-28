package org.cool.encounteryourdoom.Service;

import org.cool.encounteryourdoom.Repository.CreatureRepository;
import org.springframework.stereotype.Service;

@Service
public class CreatureService {

    //TODO : Implement CreatureService

    private final CreatureRepository creatureRepository;

    CreatureService(CreatureRepository creatureRepository) {
        this.creatureRepository = creatureRepository;
    }
}
