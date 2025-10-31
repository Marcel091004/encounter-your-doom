package org.cool.encounteryourdoom.Service;

import org.cool.encounteryourdoom.Mapper.CreatureMapper;
import org.cool.encounteryourdoom.Repository.CreatureRepository;
import org.cool.encounteryourdoom.Repository.Filter.CreatureParameterFilter;
import org.cool.encounteryourdoom.model.CreatureEntity;
import org.openapitools.model.Creature;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CreatureService {

	//TODO : Implement CreatureService

	private final CreatureRepository creatureRepository;
    private final CreatureMapper creatureMapper;

	CreatureService(CreatureRepository creatureRepository, CreatureMapper creatureMapper) {
		this.creatureRepository = creatureRepository;
        this.creatureMapper = creatureMapper;
    }



	public List<Creature> getAllCreatures(CreatureParameterFilter filter) {
		List<CreatureEntity> entities = this.creatureRepository.findCreaturesByFilters(filter);
        return creatureMapper.toCreatureList(entities);
	}

    public Creature getCreatureByID(UUID ID) {
        CreatureEntity entity = this.creatureRepository.findById(ID).orElse(null);
        return creatureMapper.toCreature(entity);

    }

    public void updateCreatureByID(UUID id, Creature creature) {
        Optional<CreatureEntity> optionalCreatureEntity = this.creatureRepository.findById(id);

            CreatureEntity oldCreature = optionalCreatureEntity.get();
            CreatureEntity newCreature = creatureMapper.toCreatureEntity(creature);
            newCreature.setId(oldCreature.getId());
            this.creatureRepository.save(newCreature);

    }

}
