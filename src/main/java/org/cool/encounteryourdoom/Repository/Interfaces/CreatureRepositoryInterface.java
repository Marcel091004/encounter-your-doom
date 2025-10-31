package org.cool.encounteryourdoom.Repository.Interfaces;

import org.cool.encounteryourdoom.Repository.Filter.CreatureParameterFilter;
import org.cool.encounteryourdoom.model.CreatureEntity;

import java.util.List;

public interface CreatureRepositoryInterface {

    List<CreatureEntity> findCreaturesByFilters(CreatureParameterFilter filter);
}
