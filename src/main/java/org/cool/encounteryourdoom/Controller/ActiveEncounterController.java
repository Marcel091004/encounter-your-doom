package org.cool.encounteryourdoom.Controller;

import org.openapitools.api.ActiveEncounterApi;
import org.openapitools.model.ActiveEncounter;
import org.openapitools.model.StatusEffects;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class ActiveEncounterController implements ActiveEncounterApi {

	@Override
	public ResponseEntity<ActiveEncounter> getActiveEncounterForUser(UUID userId) {
		//TODO this is not yet implemented
		ActiveEncounter activeEncounter = new ActiveEncounter(); // Replace with actual active encounter object
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
		//TODO this is not yet implemented
		return ResponseEntity.ok().build();

	}

	@Override
	public ResponseEntity<Void> closeActiveEncounterForUser(UUID userId) {
		//TODO this is not yet implemented
		return ResponseEntity.ok().build();

	}

}
