package org.cool.encounteryourdoom.Repository.Filter;

import lombok.Getter;
import lombok.Setter;
import org.openapitools.model.Rarity;
import org.openapitools.model.Region;

@Getter
@Setter
public class CreatureParameterFilter {

    private Region region;
    private Rarity rarity;
    private String CR;

}

