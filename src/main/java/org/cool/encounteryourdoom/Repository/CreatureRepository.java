package org.cool.encounteryourdoom.Repository;

import org.cool.encounteryourdoom.model.CreatureEntity;
import org.openapitools.model.Rarity;
import org.openapitools.model.Region;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CreatureRepository extends MongoRepository<CreatureEntity, UUID> {
	// nur Region
	List<CreatureEntity> findByRegion(Region region);

	// nur Rarity
	List<CreatureEntity> findByRarity(Rarity rarity);

	// nur CR
	List<CreatureEntity> findByCr(String cr);

	// Region + Rarity
	List<CreatureEntity> findByRegionAndRarity(Region region, Rarity rarity);

	// Region + CR
	List<CreatureEntity> findByRegionAndCr(Region region, String cr);

	// Rarity + CR
	List<CreatureEntity> findByRarityAndCr(Rarity rarity, String cr);

	// Region + Rarity + CR
	List<CreatureEntity> findByRegionAndRarityAndCr(Region region, Rarity rarity, String cr);
}
