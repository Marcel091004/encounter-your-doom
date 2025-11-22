package org.cool.encounteryourdoom.repository.Interfaces;

import org.cool.encounteryourdoom.model.PrivateEncounterEntity;
import org.cool.encounteryourdoom.repository.filter.PrivateEncounterParameterFilter;

import java.util.List;
import java.util.UUID;

public interface PrivatEncounterRepositoryInterface {

	List<PrivateEncounterEntity> findEncountersByFilters(PrivateEncounterParameterFilter filter);

}
