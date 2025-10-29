package org.cool.encounteryourdoom.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.openapitools.model.Encounter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Document("Encounter")
public class EncounterEntity extends Encounter {

    @MongoId
    private UUID id;
}
