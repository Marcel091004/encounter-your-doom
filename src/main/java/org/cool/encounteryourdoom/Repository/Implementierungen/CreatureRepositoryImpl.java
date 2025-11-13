package org.cool.encounteryourdoom.Repository.Implementierungen;

import org.cool.encounteryourdoom.Repository.Filter.CreatureParameterFilter;
import org.cool.encounteryourdoom.Repository.Interfaces.CreatureRepositoryInterface;
import org.cool.encounteryourdoom.model.CreatureEntity;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
        List<Criteria> criteriaList = new ArrayList<>();

        if (filter != null) {
            if (filter.getRegion() != null) {
                criteriaList.add(Criteria.where("region").is(filter.getRegion()));
            }
            if (filter.getRarity() != null) {
                criteriaList.add(Criteria.where("rarity").is(filter.getRarity()));
            }
            if (filter.getCr() != null && !filter.getCr().isEmpty()) {
                criteriaList.add(Criteria.where("cr").is(filter.getCr()));
            }
            if (filter.getUuid() != null) {
                criteriaList.add(Criteria.where("uuid").is(filter.getUuid()));
            }
            if (criteriaList.isEmpty()) {
                System.out.println("No filters applied, returning all encounters.");
                query = new Query();
            }
        }

        criteriaList.forEach(query::addCriteria);
        return mongoTemplate.find(query, CreatureEntity.class);
    }


}
