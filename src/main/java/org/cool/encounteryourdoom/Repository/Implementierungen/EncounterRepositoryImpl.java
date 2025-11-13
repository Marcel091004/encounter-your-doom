package org.cool.encounteryourdoom.Repository.Implementierungen;

import org.cool.encounteryourdoom.Repository.Filter.EncounterParameterFilter;
import org.cool.encounteryourdoom.Repository.Interfaces.EncounterRepositoryInterface;
import org.cool.encounteryourdoom.model.EncounterEntity;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;

import java.sql.SQLOutput;
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
				System.out.println("Filtering by region: " + filter.getRegion());
				System.out.println(Criteria.where("region").is(filter.getRegion()));
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
				System.out.println("No filters applied, returning all encounters.");
				query = new Query();
	        }

			criteriaList.forEach(query::addCriteria);

			System.out.println(query.getQueryObject());
	        return mongoTemplate.find(query, EncounterEntity.class);
	    }

	    return List.of();
	}
}
