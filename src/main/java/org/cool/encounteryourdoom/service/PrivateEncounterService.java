package org.cool.encounteryourdoom.service;

import org.cool.encounteryourdoom.mapper.PrivateEncounterMapper;
import org.cool.encounteryourdoom.model.EncounterEntity;
import org.cool.encounteryourdoom.model.PrivateEncounterEntity;
import org.cool.encounteryourdoom.repository.PrivateEncounterRepository;
import org.cool.encounteryourdoom.repository.filter.PrivateEncounterParameterFilter;
import org.openapitools.model.Encounter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PrivateEncounterService {

	private final PrivateEncounterRepository privateEncounterRepository;
	private final PrivateEncounterMapper privateEncounterMapper;
	private final ActiveEncounterService activeEncounterService;

	public PrivateEncounterService(PrivateEncounterRepository privateEncounterRepository, PrivateEncounterMapper privateEncounterMapper, ActiveEncounterService activeEncounterService) {
		this.privateEncounterRepository = privateEncounterRepository;
		this.privateEncounterMapper = privateEncounterMapper;
		this.activeEncounterService = activeEncounterService;
	}

	public List<EncounterEntity> getEncounterList(PrivateEncounterParameterFilter filter) {
		List<PrivateEncounterEntity> privateEncounters = this.privateEncounterRepository.findEncountersByFilters(filter);
		return privateEncounterMapper.toEncounterEntity(privateEncounters);
	}

    //TODO why do we need the userId again... the encounter already has a Unique ID so there is like 0 Chance you can get the wrong one
    public EncounterEntity getEncounterByID(UUID userId, UUID encounterId) {
        PrivateEncounterEntity privateEncounters = this.privateEncounterRepository.findById(encounterId).get();
        return privateEncounterMapper.toEncounterEntity(privateEncounters);
    }

    public void updateEncounterByID(UUID userId, UUID encounterId, Encounter updatedEncounter) {
       this.privateEncounterRepository.findById(encounterId);

       PrivateEncounterEntity privateEncounterEntity = privateEncounterMapper.toPrivatEncounterEntity(updatedEncounter);
	   privateEncounterEntity.setUserId(userId);
       this.privateEncounterRepository.save(privateEncounterEntity);
    }

	public void startEncounter(UUID encounterId) {
		PrivateEncounterEntity privateEncounterEntity = this.privateEncounterRepository.findById(encounterId).get();
		activeEncounterService.createActiveEncounterForUser(privateEncounterEntity.getUserId(), encounterId);
	}

	public void createPrivateEncounter(UUID userId, Encounter encounter) {
		PrivateEncounterEntity privateEncounterEntity = privateEncounterMapper.toPrivatEncounterEntity(encounter);

		privateEncounterEntity.setId(UUID.randomUUID());
		privateEncounterEntity.setUserId(userId);

		privateEncounterRepository.save(privateEncounterEntity);
	}
}
