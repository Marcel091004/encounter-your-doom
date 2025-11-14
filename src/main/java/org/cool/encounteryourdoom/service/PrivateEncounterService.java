package org.cool.encounteryourdoom.service;

import org.cool.encounteryourdoom.mapper.PrivateEncounterMapper;
import org.cool.encounteryourdoom.model.EncounterEntity;
import org.cool.encounteryourdoom.model.PrivateEncounterEntity;
import org.cool.encounteryourdoom.repository.PrivateEncounterRepository;
import org.cool.encounteryourdoom.repository.filter.PrivateEncounterParameterFilter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivateEncounterService {

	private final PrivateEncounterRepository privateEncounterRepository;
	private final PrivateEncounterMapper privateEncounterMapper;

	public PrivateEncounterService(PrivateEncounterRepository privateEncounterRepository, PrivateEncounterMapper privateEncounterMapper) {
		this.privateEncounterRepository = privateEncounterRepository;
		this.privateEncounterMapper = privateEncounterMapper;
	}

	public List<EncounterEntity> getEncounterList(PrivateEncounterParameterFilter filter) {
		List<PrivateEncounterEntity> privateEncounters = this.privateEncounterRepository.findEncountersByFilters(filter);
		return privateEncounterMapper.toEncounterEntity(privateEncounters);
	}
}
