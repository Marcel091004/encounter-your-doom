package org.cool.encounteryourdoom.Controller;

import org.cool.encounteryourdoom.Repository.Filter.EncounterParameterFilter;
import org.cool.encounteryourdoom.Service.EncounterService;
import org.openapitools.api.EncounterApi;
import org.openapitools.model.DifficultyLevel;
import org.openapitools.model.Encounter;
import org.openapitools.model.Rarity;
import org.openapitools.model.Region;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/datev/v1")
public class EncounterController implements EncounterApi {

	private final EncounterService encounterService;

	public EncounterController(EncounterService encounterService) {
		this.encounterService = encounterService;
	}

	@Override
	public ResponseEntity<List<Encounter>> getAllPublicEncounters(Region region, Rarity rarity, DifficultyLevel difficultyLevel, Integer partyLevel) {

		EncounterParameterFilter filter = new EncounterParameterFilter();
		filter.setRegion(region);
		filter.setRarity(rarity);
		filter.setDifficultyLevel(difficultyLevel);
		filter.setPartyLevel(partyLevel);

		List<Encounter> encounters = encounterService.getAllPublicEncounters(filter);
		return ResponseEntity.ok(encounters);
	}

	@Override
	public ResponseEntity<Encounter> getPublicEncounterById(UUID id) {
		Encounter encounter = encounterService.getEncounterById(id);
		return ResponseEntity.ok(encounter);
	}

	@Override
	public ResponseEntity<Void> updatePublicEncounterById(UUID id, Encounter encounter) {
		encounterService.updateEncounter(id, encounter);
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<Void> moveEncounterToUserSpace(UUID id, UUID userId) {
		//TODO this is not yet implemented
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<Void> createEncounter(Encounter encounter) {
		//TODO this is not yet implemented
		Void response = null;
		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<Encounter> getRandomEncounter(Region region, Rarity rarity, DifficultyLevel difficultyLevel, Integer partyLevel) {
		EncounterParameterFilter filter = new EncounterParameterFilter();
		filter.setRegion(region);
		filter.setRarity(rarity);
		filter.setDifficultyLevel(difficultyLevel);
		filter.setPartyLevel(partyLevel);

		Encounter encounter = encounterService.getRandomEncounter();
		return ResponseEntity.ok(encounter);
	}

}
