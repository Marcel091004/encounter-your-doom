package org.cool.encounteryourdoom.Repository.Implementierungen;

import org.cool.encounteryourdoom.Repository.Filter.EncounterParameterFilter;
import org.cool.encounteryourdoom.Repository.Interfaces.EncounterRepositoryInterface;
import org.cool.encounteryourdoom.model.EncounterEntity;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class EncounterRepositoryImpl implements EncounterRepositoryInterface {

	private final MongoTemplate mongoTemplate;

	public EncounterRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public List<EncounterEntity> findEncountersByFilters(EncounterParameterFilter filter) {
		Query query = new Query();
		Criteria criteria = new Criteria();

		if (filter != null) {
			if (filter.getRegion() != null) {
				criteria = criteria.and("region").is(filter.getRegion());
			}
			if (filter.getRarity() != null) {
				criteria = criteria.and("rarity").is(filter.getRarity());
			}
			if (filter.getDifficultyLevel() != null) {
				criteria = criteria.and("difficultyLevel").is(filter.getDifficultyLevel());
			}
			if (filter.getPartyLevel() != null) {
				criteria = criteria.and("partyLevel").is(filter.getPartyLevel());
			}
			query.addCriteria(criteria);
			return mongoTemplate.find(query, EncounterEntity.class);
		}

		return List.of();
	}
}
