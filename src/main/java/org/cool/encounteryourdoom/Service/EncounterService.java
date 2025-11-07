package org.cool.encounteryourdoom.Service;

import org.cool.encounteryourdoom.Mapper.EncounterMapper;
import org.cool.encounteryourdoom.Repository.EncounterRepository;
import org.cool.encounteryourdoom.Repository.Filter.EncounterParameterFilter;
import org.cool.encounteryourdoom.model.EncounterEntity;
import org.openapitools.model.Encounter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EncounterService {

	//TODO : Implement EncounterService

	private final EncounterRepository encounterRepository;
	private final EncounterMapper encounterMapper;

	EncounterService(EncounterRepository encounterRepository, EncounterMapper encounterMapper) {
		this.encounterRepository = encounterRepository;
		this.encounterMapper = encounterMapper;
	}

	public List<Encounter> getAllPublicEncounters(EncounterParameterFilter filter) {
		List<EncounterEntity> entities = this.encounterRepository.findEncountersByFilters(filter);
		return encounterMapper.toEncounterList(entities);
	}

	public Encounter getEncounterById(UUID id) {
		EncounterEntity entity = this.encounterRepository.findById(id).orElse(null);
		return encounterMapper.toEncounter(entity);
	}

	public UUID createEncounter(Encounter encounter) {
		EncounterEntity entity = encounterMapper.toEncounterEntity(encounter);
		UUID id = UUID.randomUUID();
		entity.setId(id);
		this.encounterRepository.save(entity);
		return id;
	}

	public Encounter getRandomEncounter() {
		List<EncounterEntity> entities = this.encounterRepository.findEncountersByFilters(new EncounterParameterFilter());
		if (entities.isEmpty()) {
			return null;
		}
		int randomIndex = (int) (Math.random() * entities.size());
		EncounterEntity randomEntity = entities.get(randomIndex);
		return encounterMapper.toEncounter(randomEntity);
	}

	public void updateEncounter(UUID id, Encounter encounter) {
		EncounterEntity entity = this.encounterRepository.findById(id).orElse(null);
		if (entity != null) {
			EncounterEntity updatedEntity = encounterMapper.toEncounterEntity(encounter);
			updatedEntity.setId(id);
			this.encounterRepository.save(updatedEntity);
		} else {
			throw new IllegalArgumentException("Encounter with ID " + id + " does not exist.");
		}
	}
}
