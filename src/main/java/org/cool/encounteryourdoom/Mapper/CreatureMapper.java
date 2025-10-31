package org.cool.encounteryourdoom.Mapper;


import org.cool.encounteryourdoom.model.CreatureEntity;
import org.mapstruct.Mapper;
import org.openapitools.model.Creature;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CreatureMapper {

    CreatureEntity toCreatureEntity(Creature c);
    Creature toCreature(CreatureEntity ce);

    List<CreatureEntity> toCreatureEntityList(List<Creature> c);
    List<Creature> toCreatureList(List<CreatureEntity> ce);
}
