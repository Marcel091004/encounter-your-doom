package org.cool.encounteryourdoom.repository.Interfaces;

import org.cool.encounteryourdoom.model.CreatureEntity;
import org.cool.encounteryourdoom.repository.filter.CreatureParameterFilter;

import java.util.List;

public interface CreatureRepositoryInterface {

    List<CreatureEntity> findCreaturesByFilters(CreatureParameterFilter filter);
}
