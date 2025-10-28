package org.cool.encounteryourdoom.Controller;

import org.openapitools.api.EncounterApi;
import org.openapitools.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class EncounterController implements EncounterApi {

    @Override
    public ResponseEntity<List<Encounter>> getAllPublicEncounters(Region region, Rarity rarity, DifficultyLevel difficultyLevel, Integer partyLevel) {
        //TODO this is not yet implemented
        List<Encounter> encounters = List.of(); // Replace with actual encounter objects
        return ResponseEntity.ok(encounters);
    }

    @Override
    public ResponseEntity<Encounter> getPublicEncounterById(UUID id) {
        //TODO this is not yet implemented
        Encounter encounters = new Encounter(); // Replace with actual encounter objects
        return ResponseEntity.ok(encounters);
    }

    @Override
    public ResponseEntity<Void> updatePublicEncounterById(UUID id, Encounter encounter) {
        //TODO this is not yet implemented
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> moveEncounterToUserSpace(UUID id, UUID userId) {
        //TODO this is not yet implemented
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<CreateNewCreature200Response> createNewEncounter(Encounter encounter) {
        //TODO this is not yet implemented
        CreateNewCreature200Response response = new CreateNewCreature200Response();
        response.setId(UUID.randomUUID());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<CreateNewCreature200Response> createNewEncounterForUser(UUID id, Encounter encounter) {
        //TODO this is not yet implemented
        CreateNewCreature200Response response = new CreateNewCreature200Response();
        response.setId(UUID.randomUUID());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Encounter> getRandomEncounter(Region region, Rarity rarity, DifficultyLevel difficultyLevel, Integer partyLevel) {
        //TODO this is not yet implemented
        Encounter encounter = new Encounter(); // Replace with actual encounter object
        return ResponseEntity.ok(encounter);
    }

    @Override
    public ResponseEntity<List<Encounter>> getAllEncountersForUser(UUID userId) {
        //TODO this is not yet implemented
        List<Encounter> encounter = List.of(); // Replace with actual encounter object
        return ResponseEntity.ok(encounter);
    }

    @Override
    public ResponseEntity<Encounter> getEncounterForUser(UUID userId, UUID id) {
        //TODO this is not yet implemented
        Encounter encounter = new Encounter(); // Replace with actual encounter object
        return ResponseEntity.ok(encounter);
    }

    @Override
    public ResponseEntity<Void> updateEncounterForUser(UUID userId, UUID id, Encounter encounter) {
        //TODO this is not yet implemented// Replace with actual encounter object
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> startEncounterForUser(UUID userId, UUID id) {
        //TODO this is not yet implemented
        return ResponseEntity.ok().build();
    }


}
