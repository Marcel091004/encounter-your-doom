package org.cool.encounteryourdoom.controller;

import org.cool.encounteryourdoom.mapper.PrivateEncounterMapper;
import org.cool.encounteryourdoom.model.EncounterEntity;
import org.cool.encounteryourdoom.repository.filter.PrivateEncounterParameterFilter;
import org.cool.encounteryourdoom.service.PrivateEncounterService;
import org.openapitools.api.PrivateEncounterApi;
import org.openapitools.model.DifficultyLevel;
import org.openapitools.model.Encounter;
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
public class PrivateEncounterController implements PrivateEncounterApi {

	private final PrivateEncounterService privateEncounterService;
	private final PrivateEncounterMapper privateEncounterMapper;

	public PrivateEncounterController(PrivateEncounterService privateEncounterService, PrivateEncounterMapper privateEncounterMapper) {
		this.privateEncounterService = privateEncounterService;
		this.privateEncounterMapper = privateEncounterMapper;
	}

	@Override
	public ResponseEntity<List<Encounter>> getAllEncountersForUser(
            UUID userId,
            Region region,
            Rarity rarity,
            DifficultyLevel difficultyLevel,
            Integer partyLevel
    ) {

		PrivateEncounterParameterFilter filter = new PrivateEncounterParameterFilter();
		filter.setUserId(userId);

		filter.setRegion(region);
		filter.setRarity(rarity);
		filter.setDifficultyLevel(difficultyLevel);
		filter.setPartyLevel(partyLevel);

		List<EncounterEntity> encounters = privateEncounterService.getEncounterList(filter);

		return ResponseEntity.ok(privateEncounterMapper.toEncounter(encounters));
	}

	@Override
	public ResponseEntity<Encounter> getEncounterForUser(UUID userId, UUID id) {
		EncounterEntity encounter = privateEncounterService.getEncounterByID(userId, id);
		return ResponseEntity.ok(encounter);
	}

	@Override
	public ResponseEntity<Void> updateEncounterForUser(UUID userId, UUID id, Encounter encounter) {
        privateEncounterService.updateEncounterByID(userId, id, encounter);
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Void> startEncounterForUser(UUID id, UUID userId) {
		privateEncounterService.startEncounter(id);
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Void> createEncounterForUser(UUID id, Encounter encounter) {
		privateEncounterService.createPrivateEncounter(id, encounter);
		URI location = URI.create(String.format("/datev/v1/privateEncounter/%s", encounter.getId()));
		return ResponseEntity.created(location).build();
	}

	@Override
	public ResponseEntity<Void> deleteEncounterForUser(UUID id, UUID userId) {
		privateEncounterService.deleteEncounterByID(id, userId);
		return ResponseEntity.noContent().build();
	}

}
