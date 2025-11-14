package org.cool.encounteryourdoom.repository;

import org.cool.encounteryourdoom.model.PrivateEncounterEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PrivatEncounterRepository extends MongoRepository<PrivateEncounterEntity, UUID>  {

}
