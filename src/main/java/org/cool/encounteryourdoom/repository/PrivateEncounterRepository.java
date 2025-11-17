package org.cool.encounteryourdoom.repository;

import org.cool.encounteryourdoom.model.PrivateEncounterEntity;
import org.cool.encounteryourdoom.repository.Interfaces.PrivatEncounterRepositoryInterface;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PrivateEncounterRepository extends MongoRepository<PrivateEncounterEntity, UUID>, PrivatEncounterRepositoryInterface {

}
