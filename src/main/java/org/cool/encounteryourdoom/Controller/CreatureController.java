package org.cool.encounteryourdoom.Controller;

import org.cool.encounteryourdoom.Repository.Filter.CreatureParameterFilter;
import org.cool.encounteryourdoom.Service.CreatureService;
import org.openapitools.api.CreatureApi;
import org.openapitools.model.Creature;
import org.openapitools.model.Rarity;
import org.openapitools.model.Region;
import org.springframework.data.crossstore.ChangeSetPersister;
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

        CreatureParameterFilter filter = new CreatureParameterFilter();
        filter.setRegion(region);
        filter.setRarity(rarity);
        filter.setCR(CR);

        return ResponseEntity.ok(creatureService.getAllCreatures(filter));
    }

    @Override
    public ResponseEntity<Creature> getCreatureById(UUID id) {

        Creature creature = creatureService.getCreatureByID(id);
        return ResponseEntity.ok(creature);

    }

    @Override
    public ResponseEntity<Void> updateCreatureById(UUID id, Creature creature) {

		creatureService.updateCreatureByID(id, creature);
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
