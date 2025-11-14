package org.cool.encounteryourdoom.mapper;

import org.cool.encounteryourdoom.model.EncounterEntity;
import org.cool.encounteryourdoom.model.PrivateEncounterEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PrivatEncounterMapper {
	PrivateEncounterEntity toPrivatEncounterEntity(EncounterEntity e);

	EncounterEntity toEncounterEntity(PrivateEncounterEntity pe);
}
