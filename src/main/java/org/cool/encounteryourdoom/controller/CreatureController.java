package org.cool.encounteryourdoom.controller;

import org.cool.encounteryourdoom.repository.filter.CreatureParameterFilter;
import org.cool.encounteryourdoom.service.CreatureService;
import org.openapitools.api.CreatureApi;
import org.openapitools.model.Creature;
import org.openapitools.model.Rarity;
import org.openapitools.model.Region;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/datev/v1")
public class CreatureController implements CreatureApi {

    private final CreatureService creatureService;

    public CreatureController(CreatureService creatureService) {
        this.creatureService = creatureService;
    }

    @Override
    public ResponseEntity<List<Creature>> getCreatures(Region region, Rarity rarity, String CR) {

        CreatureParameterFilter filter = new CreatureParameterFilter();
        filter.setRegion(region);
        filter.setRarity(rarity);
        filter.setCr(CR);

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
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> createCreature(Creature creature) {
		UUID response = creatureService.createCreature(creature);
        URI location = URI.create("/datev/v1/creature/" + response);
        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<Creature> getRandomCreature(Region region, Rarity rarity, String CR) {
        //TODO check why this also shows Objects with null in the respective parameter
		CreatureParameterFilter filter = new CreatureParameterFilter();
		filter.setRegion(region);
		filter.setRarity(rarity);
		filter.setCr(CR);

		return ResponseEntity.ok(creatureService.getRandomCreature(filter));
    }
}
