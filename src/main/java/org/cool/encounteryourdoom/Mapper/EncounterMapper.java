package org.cool.encounteryourdoom.Mapper;

import org.cool.encounteryourdoom.model.EncounterEntity;
import org.mapstruct.Mapper;
import org.openapitools.model.Encounter;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EncounterMapper {

	EncounterEntity toEncounterEntity(Encounter e);
	Encounter toEncounter(EncounterEntity ee);

	List<EncounterEntity> toEncounterEntityList(List<Encounter> e);
	List<Encounter> toEncounterList(List<EncounterEntity> ee);
}
