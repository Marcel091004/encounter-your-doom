package org.cool.encounteryourdoom.service;

import org.cool.encounteryourdoom.mapper.PrivateEncounterMapper;
import org.cool.encounteryourdoom.model.EncounterEntity;
import org.cool.encounteryourdoom.model.PrivateEncounterEntity;
import org.cool.encounteryourdoom.repository.EncounterRepository;
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
	private final EncounterRepository encounterRepository;
	private final PrivateEncounterMapper privateEncounterMapper;
	private final ActiveEncounterService activeEncounterService;

	public PrivateEncounterService(PrivateEncounterRepository privateEncounterRepository, PrivateEncounterMapper privateEncounterMapper, ActiveEncounterService activeEncounterService, EncounterRepository encounterRepository) {
		this.privateEncounterRepository = privateEncounterRepository;
		this.encounterRepository = encounterRepository;
		this.privateEncounterMapper = privateEncounterMapper;
		this.activeEncounterService = activeEncounterService;
	}

	public List<EncounterEntity> getEncounterList(PrivateEncounterParameterFilter filter) {
		List<PrivateEncounterEntity> privateEncounters = this.privateEncounterRepository.findEncountersByFilters(filter);
		return privateEncounterMapper.toEncounterEntity(privateEncounters);
	}

    public EncounterEntity getEncounterByID(UUID userId, UUID encounterId) {

		PrivateEncounterEntity privateEncounterEntity;
		List<PrivateEncounterEntity> privateEncounters;
        PrivateEncounterParameterFilter filter = new PrivateEncounterParameterFilter();
        filter.setUserId(userId);

		try {

         privateEncounters = this.privateEncounterRepository.findEncountersByFilters(filter);
         privateEncounterEntity = privateEncounters.stream()
                 .filter(encounter -> encounter.getId().equals(encounterId))
                 .findFirst()
                 .orElseThrow(() -> new IllegalArgumentException("Encounter not found for the given user."));

		} catch (Exception e) {
            privateEncounterEntity = privateEncounterMapper.toPrivatEncounterEntity(this.encounterRepository.findById(encounterId).get());
		}
        return privateEncounterMapper.toEncounterEntity(privateEncounterEntity);
    }

    public void updateEncounterByID(UUID encounterId, UUID userId, Encounter updatedEncounter) {
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

	public void deleteEncounterByID(UUID encounterId, UUID userId) {
		Optional<PrivateEncounterEntity> privateEncounterEntity = privateEncounterRepository.findById(encounterId);

		if (privateEncounterEntity.isPresent() && privateEncounterEntity.get().getUserId().equals(userId)) {
			privateEncounterRepository.deleteByUserIdAndId(userId, encounterId);
		} else {
			throw new IllegalArgumentException("Encounter not found or does not belong to the user.");
		}
	}
}
