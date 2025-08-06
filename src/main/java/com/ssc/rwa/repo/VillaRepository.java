package com.ssc.rwa.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ssc.rwa.model.Villa;

@Repository
public interface VillaRepository extends MongoRepository<Villa, String> {

	// Check if a villa with the given number already exists
	boolean existsByVillaNumber(String number);

	// Find a villa by its unique number
	Optional<Villa> findByVillaNumber(String number);

	// Find all villas in a specific line/road number (optional filter for UI)
	List<Villa> findByRoadNumber(String line);

	// Find all villas owned by a specific owner
	List<Villa> findByOwnerNameContainingIgnoreCase(String ownerName);

	// Find all villas by occupancy status (occupied/unoccupied)
	List<Villa> findByIsOccupied(Boolean isOccupied);

}
