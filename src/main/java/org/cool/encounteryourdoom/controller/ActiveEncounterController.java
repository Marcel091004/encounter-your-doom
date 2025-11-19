package org.cool.encounteryourdoom.controller;

import org.cool.encounteryourdoom.service.ActiveEncounterService;
import org.openapitools.api.ActiveEncounterApi;
import org.openapitools.model.ActiveEncounter;
import org.openapitools.model.StatusEffects;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("datev/v1")
public class ActiveEncounterController implements ActiveEncounterApi {

    private final ActiveEncounterService activeEncounterService;

    public ActiveEncounterController(ActiveEncounterService activeEncounterService) {
        this.activeEncounterService = activeEncounterService;
    }


    @Override
	public ResponseEntity<ActiveEncounter> getActiveEncounterForUser(UUID userId) {

		ActiveEncounter activeEncounter = this.activeEncounterService.getActiveEncounter(userId);
		return ResponseEntity.ok(activeEncounter);

	}

	@Override
	public ResponseEntity<Void> updateCreatureInActiveEncounterForUser(
			UUID userId,
			UUID creatureId,
			Integer heal,
			Integer damage,
			List<StatusEffects> statusEffect
	) {

        this.activeEncounterService.updateCreatureInActiveEncounter(userId, creatureId, heal, damage, statusEffect);
		return ResponseEntity.noContent().build();

	}

	@Override
	public ResponseEntity<Void> closeActiveEncounterForUser(UUID userId) {

        this.activeEncounterService.deleteActiveEncounter(userId);
		return ResponseEntity.noContent().build();

	}

}
