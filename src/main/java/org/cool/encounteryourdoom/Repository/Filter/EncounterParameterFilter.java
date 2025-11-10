package org.cool.encounteryourdoom.Repository.Filter;

import lombok.Getter;
import lombok.Setter;
import org.openapitools.model.DifficultyLevel;
import org.openapitools.model.Rarity;
import org.openapitools.model.Region;

@Getter
@Setter
public class EncounterParameterFilter {
	private Region region;
	private Rarity rarity;
	private DifficultyLevel difficultyLevel;
	private Integer partyLevel;

}
