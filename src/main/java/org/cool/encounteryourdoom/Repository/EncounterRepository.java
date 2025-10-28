package org.cool.encounteryourdoom.Repository;

import org.openapitools.model.Encounter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EncounterRepository extends MongoRepository<List<Encounter>, UUID> {
}
