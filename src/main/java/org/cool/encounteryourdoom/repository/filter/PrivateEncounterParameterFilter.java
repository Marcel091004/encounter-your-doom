package org.cool.encounteryourdoom.repository.filter;

import lombok.Getter;
import lombok.Setter;
import org.openapitools.model.DifficultyLevel;
import org.openapitools.model.Rarity;
import org.openapitools.model.Region;

import java.util.UUID;

@Setter
@Getter
public class PrivateEncounterParameterFilter {
	private UUID userId;

	private Region region;
	private Rarity rarity;
	private DifficultyLevel difficultyLevel;
	private Integer partyLevel;
}
