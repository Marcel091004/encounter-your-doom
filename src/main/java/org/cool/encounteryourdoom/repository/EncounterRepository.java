package org.cool.encounteryourdoom.repository;

import org.cool.encounteryourdoom.repository.Interfaces.EncounterRepositoryInterface;
import org.cool.encounteryourdoom.model.EncounterEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EncounterRepository extends MongoRepository<EncounterEntity, UUID>, EncounterRepositoryInterface {

}
