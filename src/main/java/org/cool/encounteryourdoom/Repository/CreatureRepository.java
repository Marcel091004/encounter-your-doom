package org.cool.encounteryourdoom.Repository;

import org.cool.encounteryourdoom.Repository.Interfaces.CreatureRepositoryInterface;
import org.cool.encounteryourdoom.model.CreatureEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CreatureRepository extends MongoRepository<CreatureEntity, UUID>, CreatureRepositoryInterface {

	void updateCreatureByID(UUID id, CreatureEntity creatureEntity);

}
