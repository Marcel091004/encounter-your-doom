package org.cool.encounteryourdoom.Repository;

import org.cool.encounteryourdoom.model.privateEncounter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends MongoRepository<privateEncounter, UUID> {
    List<privateEncounter> findAllByUserId(UUID userId);
}
