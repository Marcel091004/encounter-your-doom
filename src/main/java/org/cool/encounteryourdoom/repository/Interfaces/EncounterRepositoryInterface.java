package org.cool.encounteryourdoom.repository.Interfaces;

import org.cool.encounteryourdoom.model.EncounterEntity;
import org.cool.encounteryourdoom.repository.filter.EncounterParameterFilter;

import java.util.List;

public interface EncounterRepositoryInterface {

	List<EncounterEntity> findEncountersByFilters(EncounterParameterFilter filter);
}
