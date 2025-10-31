package org.cool.encounteryourdoom.Repository.Implementierungen;

import org.cool.encounteryourdoom.Repository.Filter.CreatureParameterFilter;
import org.cool.encounteryourdoom.Repository.Interfaces.CreatureRepositoryInterface;
import org.cool.encounteryourdoom.model.CreatureEntity;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CreatureRepositoryImpl implements CreatureRepositoryInterface {

    private final MongoTemplate mongoTemplate;

    public CreatureRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public List<CreatureEntity> findCreaturesByFilters(CreatureParameterFilter filter) {
        Query query = new Query();
        Criteria criteria = new Criteria();

        if (filter != null) {
            if (filter.getRegion() != null) {
                criteria = criteria.and("region").is(filter.getRegion());
            }
            if (filter.getRarity() != null) {
                criteria = criteria.and("rarity").is(filter.getRarity());
            }
            if (filter.getCR() != null && !filter.getCR().isEmpty()) {
                criteria = criteria.and("CR").is(filter.getCR());
            }
        }
        query.addCriteria(criteria);
        return mongoTemplate.find(query, CreatureEntity.class);
    }


}
