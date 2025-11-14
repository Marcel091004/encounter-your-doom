package org.cool.encounteryourdoom.repository.Interfaces;

import org.cool.encounteryourdoom.repository.filter.EncounterParameterFilter;
import org.cool.encounteryourdoom.model.EncounterEntity;

import java.util.List;

public interface EncounterRepositoryInterface {

	List<EncounterEntity> findEncountersByFilters(EncounterParameterFilter filter);
}
