package org.cool.encounteryourdoom.repository.implementierungen;

import org.cool.encounteryourdoom.model.EncounterEntity;
import org.cool.encounteryourdoom.repository.Interfaces.EncounterRepositoryInterface;
import org.cool.encounteryourdoom.repository.filter.EncounterParameterFilter;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

public class EncounterRepositoryImpl implements EncounterRepositoryInterface {

	private final MongoTemplate mongoTemplate;

	public EncounterRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}


	@Override
	public List<EncounterEntity> findEncountersByFilters(EncounterParameterFilter filter) {
	    Query query = new Query();
	    List<Criteria> criteriaList = new ArrayList<>();

	    if (filter != null) {
	        if (filter.getRegion() != null) {
	            criteriaList.add(Criteria.where("region").is(filter.getRegion()));
	        }
	        if (filter.getRarity() != null) {
	            criteriaList.add(Criteria.where("rarity").is(filter.getRarity()));
	        }
	        if (filter.getDifficultyLevel() != null) {
	            criteriaList.add(Criteria.where("difficultyLevel").is(filter.getDifficultyLevel()));
	        }
	        if (filter.getPartyLevel() != null) {
	            criteriaList.add(Criteria.where("partyLevel").is(filter.getPartyLevel()));
	        }
	        if (criteriaList.isEmpty()) {
				query = new Query();
	        }

			criteriaList.forEach(query::addCriteria);

	        return mongoTemplate.find(query, EncounterEntity.class);
	    }

	    return List.of();
	}
}
