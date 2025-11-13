package org.cool.encounteryourdoom.Controller;

import org.openapitools.api.PrivateEncounterApi;
import org.openapitools.model.Encounter;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public class PrivateEncounterController implements PrivateEncounterApi {
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

	@Override
	public ResponseEntity<Void> createEncounterForUser(UUID id, Encounter encounter) {
		//TODO this is not yet implemented
		UUID response = UUID.randomUUID();
		return (ResponseEntity<Void>) ResponseEntity.ok();
	}

}
