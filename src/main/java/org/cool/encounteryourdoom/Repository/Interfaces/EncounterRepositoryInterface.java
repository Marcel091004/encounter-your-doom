package org.cool.encounteryourdoom.Repository.Interfaces;

import org.cool.encounteryourdoom.Repository.Filter.EncounterParameterFilter;
import org.cool.encounteryourdoom.model.EncounterEntity;

import java.util.List;

public interface EncounterRepositoryInterface {

	List<EncounterEntity> findEncountersByFilters(EncounterParameterFilter filter);
}
