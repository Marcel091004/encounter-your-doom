package org.cool.encounteryourdoom.Repository;

import org.openapitools.model.Encounter;
import org.openapitools.model.GenerateUserId200Response;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<List<Encounter>, GenerateUserId200Response> {
}
