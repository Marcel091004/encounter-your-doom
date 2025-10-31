package org.cool.encounteryourdoom.Service;

import org.cool.encounteryourdoom.Repository.Filter.CreatureParameterFilter;
import org.cool.encounteryourdoom.Repository.Implementierungen.CreatureRepositoryImpl;
import org.cool.encounteryourdoom.model.CreatureEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreatureService {

	//TODO : Implement CreatureService

	private final CreatureRepositoryImpl creatureRepository;

	CreatureService(CreatureRepositoryImpl creatureRepository) {
		this.creatureRepository = creatureRepository;
	}


	//TODO: Refactoring für weniger Methoden und einfache erweiterbarkeit (eigenes Interface?)

	public List<CreatureEntity> getAllCreatures(CreatureParameterFilter filter) {
		return this.creatureRepository.findCreaturesByFilters(filter);
	}



//   public Optional<CreatureEntity> findByUUId(UUID id) {
//         return creatureRepository.findByUUId(id);
//   }

}
