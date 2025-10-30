package org.cool.encounteryourdoom.Service;

import org.cool.encounteryourdoom.Repository.CreatureRepository;
import org.cool.encounteryourdoom.model.CreatureEntity;
import org.openapitools.model.Rarity;
import org.openapitools.model.Region;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreatureService {

	//TODO : Implement CreatureService

	private final CreatureRepository creatureRepository;

	CreatureService(CreatureRepository creatureRepository) {
		this.creatureRepository = creatureRepository;
	}

	//TODO: Refactoring für weniger Methoden und einfache erweiterbarkeit (eigenes Interface?)
	public List<CreatureEntity> getAllCreatures() {
		return this.creatureRepository.findAll();
	}

	public List<CreatureEntity> getAllCreatures(Region region) {
		return this.creatureRepository.findByRegion(region);
	}

	public List<CreatureEntity> getAllCreatures(Region region, Rarity rarity) {
		return creatureRepository.findByRegionAndRarity(region, rarity);
	}

	public List<CreatureEntity> getAllCreatures(Region region, String cr) {
		return creatureRepository.findByRegionAndCr(region, cr);
	}

	public List<CreatureEntity> getAllCreatures(Region region, Rarity rarity, String cr) {
		return creatureRepository.findByRegionAndRarityAndCr(region, rarity, cr);
	}

	public List<CreatureEntity> getAllCreatures(Rarity rarity) {
		return creatureRepository.findByRarity(rarity);
	}

	public List<CreatureEntity> getAllCreatures(Rarity rarity, String cr) {
		return creatureRepository.findByRarityAndCr(rarity, cr);
	}

	public List<CreatureEntity> getAllCreatures(String cr) {
		return creatureRepository.findByCr(cr);
	}


}
