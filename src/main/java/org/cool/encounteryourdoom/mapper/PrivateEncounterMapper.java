package org.cool.encounteryourdoom.mapper;

import org.cool.encounteryourdoom.model.EncounterEntity;
import org.cool.encounteryourdoom.model.PrivateEncounterEntity;
import org.mapstruct.Mapper;
import org.openapitools.model.Encounter;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PrivateEncounterMapper {
	PrivateEncounterEntity toPrivatEncounterEntity(EncounterEntity e);

	EncounterEntity toEncounterEntity(PrivateEncounterEntity pe);

	List<EncounterEntity> toEncounterEntity(List<PrivateEncounterEntity> peList);

	List<Encounter> toEncounterFromPrivate(List<PrivateEncounterEntity> peList);

	List<Encounter> toEncounter(List<EncounterEntity> peList);
}
