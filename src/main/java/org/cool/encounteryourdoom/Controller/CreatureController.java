package org.cool.encounteryourdoom.Controller;

import org.openapitools.api.CreatureApi;
import org.openapitools.model.CreateNewCreature200Response;
import org.openapitools.model.Creature;
import org.openapitools.model.Rarity;
import org.openapitools.model.Region;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class CreatureController implements CreatureApi {

    @Override
    public ResponseEntity<List<Creature>> getCreature(Region region, Rarity rarity, String CR) {
        //TODO this is not yet implemented
        List<Creature> creatures = new ArrayList<>(); // Replace with actual creature object
        return ResponseEntity.ok(creatures);
    }

    @Override
    public ResponseEntity<Creature> getCreatureById(UUID id) {
        //TODO this is not yet implemented
        Creature creature = new Creature(); // Replace with actual creature object
        return ResponseEntity.ok(creature);
    }

    @Override
    public ResponseEntity<Void> updateCreatureById(UUID id, Creature creature) {
        //TODO this is not yet implemented
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<CreateNewCreature200Response> createNewCreature(Creature creature) {
        //TODO this is not yet implemented
        CreateNewCreature200Response response = new CreateNewCreature200Response();
        response.setId(UUID.randomUUID());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Creature> getRandomCreature(Region region, Rarity rarity, String CR) {
        //TODO this is not yet implemented
        return ResponseEntity.ok().build();
    }
}
