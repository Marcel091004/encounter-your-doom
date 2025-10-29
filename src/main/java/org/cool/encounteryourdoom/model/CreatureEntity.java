package org.cool.encounteryourdoom.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.openapitools.model.Creature;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Document("Creatures")
public class CreatureEntity extends Creature{

    @MongoId
    private UUID id;
}
