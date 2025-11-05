package org.cool.encounteryourdoom.Service;

import org.cool.encounteryourdoom.Mapper.CreatureMapper;
import org.cool.encounteryourdoom.Repository.CreatureRepository;
import org.cool.encounteryourdoom.Repository.Filter.CreatureParameterFilter;
import org.cool.encounteryourdoom.model.CreatureEntity;
import org.openapitools.model.Creature;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CreatureService {

	private final CreatureRepository creatureRepository;
	private final CreatureMapper creatureMapper;
	private static final java.util.Random RANDOM = new java.util.Random();

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
		if (optionalCreatureEntity.isPresent()) {
			CreatureEntity oldCreature = optionalCreatureEntity.get();
			CreatureEntity newCreature = creatureMapper.toCreatureEntity(creature);
			newCreature.setId(oldCreature.getId());
			this.creatureRepository.save(newCreature);
		}
		// Optional: else-Block für Fehlerbehandlung
	}

	public UUID createCreature(Creature creature) {
		CreatureEntity entity = creatureMapper.toCreatureEntity(creature);
		UUID id = UUID.randomUUID();
		entity.setId(id);
		this.creatureRepository.save(entity);
		return id;
	}

	public Creature getRandomCreature(CreatureParameterFilter filter) {
		List<CreatureEntity> entities = this.creatureRepository.findCreaturesByFilters(filter);
		if (entities.isEmpty()) {
			return null;
		}
		int randomIndex = RANDOM.nextInt(entities.size());
		CreatureEntity entity = entities.get(randomIndex);
		return creatureMapper.toCreature(entity);
	}

}
