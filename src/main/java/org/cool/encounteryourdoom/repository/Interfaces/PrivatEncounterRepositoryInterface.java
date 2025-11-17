package org.cool.encounteryourdoom.repository.Interfaces;

import org.cool.encounteryourdoom.model.PrivateEncounterEntity;
import org.cool.encounteryourdoom.repository.filter.PrivateEncounterParameterFilter;

import java.util.List;

public interface PrivatEncounterRepositoryInterface {

	List<PrivateEncounterEntity> findEncountersByFilters(PrivateEncounterParameterFilter filter);
}
