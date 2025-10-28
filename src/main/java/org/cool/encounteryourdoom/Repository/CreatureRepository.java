package org.cool.encounteryourdoom.Repository;

import org.openapitools.model.Creature;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CreatureRepository extends MongoRepository<Creature, UUID> {
}
