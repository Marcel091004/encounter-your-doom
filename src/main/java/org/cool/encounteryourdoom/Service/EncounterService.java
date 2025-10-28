package org.cool.encounteryourdoom.Service;

import org.cool.encounteryourdoom.Repository.EncounterRepository;
import org.springframework.stereotype.Service;

@Service
public class EncounterService {

    //TODO : Implement EncounterService

    private final EncounterRepository encounterRepository;

    EncounterService(EncounterRepository encounterRepository) {
        this.encounterRepository = encounterRepository;
    }
}
