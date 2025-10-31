package org.cool.encounteryourdoom.Controller;

import org.cool.encounteryourdoom.Repository.Filter.CreatureParameterFilter;
import org.cool.encounteryourdoom.Service.CreatureService;
import org.openapitools.api.CreatureApi;
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

    private final CreatureService creatureService;

    CreatureController(CreatureService creatureService) {
        this.creatureService = creatureService;
    }


    @Override
    public ResponseEntity<List<Creature>> getCreature(Region region, Rarity rarity, String CR) {
        //TODO this is not yet implemented

        CreatureParameterFilter filter = new CreatureParameterFilter();
        filter.setRegion(region);
        filter.setRarity(rarity);
        filter.setCR(CR);

        System.out.println(creatureService.getAllCreatures(filter));

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
    public ResponseEntity<UUID> createNewCreature(Creature creature) {
        //TODO this is not yet implemented
        UUID response = UUID.randomUUID();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Creature> getRandomCreature(Region region, Rarity rarity, String CR) {
        //TODO this is not yet implemented
        return ResponseEntity.ok().build();
    }
}
