package org.cool.encounteryourdoom.repository;

import org.cool.encounteryourdoom.model.CreatureEntity;
import org.cool.encounteryourdoom.repository.Interfaces.CreatureRepositoryInterface;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CreatureRepository extends MongoRepository<CreatureEntity, UUID>, CreatureRepositoryInterface {


}
