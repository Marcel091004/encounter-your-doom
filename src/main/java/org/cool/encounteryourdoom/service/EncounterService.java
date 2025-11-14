package org.cool.encounteryourdoom.service;

import org.cool.encounteryourdoom.mapper.EncounterMapper;
import org.cool.encounteryourdoom.mapper.PrivatEncounterMapper;
import org.cool.encounteryourdoom.repository.EncounterRepository;
import org.cool.encounteryourdoom.repository.filter.EncounterParameterFilter;
import org.cool.encounteryourdoom.repository.PrivatEncounterRepository;
import org.cool.encounteryourdoom.model.EncounterEntity;
import org.cool.encounteryourdoom.model.PrivateEncounterEntity;
import org.openapitools.model.Encounter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EncounterService {

	//TODO : Implement EncounterService

	private final EncounterRepository encounterRepository;
	private final PrivatEncounterRepository privatEncounterRepository;

	private final EncounterMapper encounterMapper;
	private final PrivatEncounterMapper privatEncounterMapper;

	EncounterService(EncounterRepository encounterRepository, PrivatEncounterRepository privatEncounterRepository, EncounterMapper encounterMapper, PrivatEncounterMapper privatEncounterMapper) {
		this.encounterRepository = encounterRepository;
		this.privatEncounterRepository = privatEncounterRepository;

		this.encounterMapper = encounterMapper;
		this.privatEncounterMapper = privatEncounterMapper;
	}

	public List<Encounter> getAllPublicEncounters(EncounterParameterFilter filter) {
		List<EncounterEntity> entities = this.encounterRepository.findEncountersByFilters(filter);
		return encounterMapper.toEncounterList(entities);
	}

	public Encounter getEncounterById(UUID id) {
		EncounterEntity entity = this.encounterRepository.findById(id).get();
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

	public void moveEncounterToUserSpace(UUID id, UUID userId) {
		EncounterEntity entity = this.encounterRepository.findById(id).orElse(null);
		PrivateEncounterEntity privateEntity = privatEncounterMapper.toPrivatEncounterEntity(entity);
		if (entity != null) {
			privateEntity.setUserId(userId);
			this.privatEncounterRepository.save(privateEntity);
		} else {
			throw new IllegalArgumentException("Encounter with ID " + id + " does not exist.");
		}
	}

}
