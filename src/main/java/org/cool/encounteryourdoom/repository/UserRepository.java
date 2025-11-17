package org.cool.encounteryourdoom.repository;

import org.cool.encounteryourdoom.model.PrivateEncounterEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends MongoRepository<PrivateEncounterEntity, UUID> {
    List<PrivateEncounterEntity> findAllByUserId(UUID userId);
}
