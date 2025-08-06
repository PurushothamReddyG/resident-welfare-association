package com.ssc.rwa.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssc.rwa.exception.BadRequestException;
import com.ssc.rwa.exception.ResourceNotFoundException;
import com.ssc.rwa.model.Villa;
import com.ssc.rwa.repo.VillaRepository;
import com.ssc.rwa.service.VillaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VillaServiceImpl implements VillaService {

	@Autowired
	private VillaRepository villaRepository;

	@Override
	public List<Villa> getAllVillas() {
		return villaRepository.findAll();
	}

	@Override
	public Villa getVillaDetailsByNumber(String number) {
		return villaRepository.findById(number)
			.orElseThrow(() -> new ResourceNotFoundException("Villa not found with number: " + number));
	}

	@Override
	public Villa addNewVillaDetails(Villa villa) {
		log.info("Creating villa: {}", villa.getVillaNumber());
		if (villaRepository.existsByVillaNumber(villa.getVillaNumber())) {
			log.warn("Villa with number {} already exists", villa.getVillaNumber());
			throw new BadRequestException("Villa number already exists");
		}
		Villa response = villaRepository.save(villa);
		log.info("Villa details are added for villa no : {}", response.getVillaNumber());
		return response;
	}

	@Override
	public Villa updateVilla(String number, Villa villa) {
		Villa existing = villaRepository.findByVillaNumber(number)
			.orElseThrow(() -> new ResourceNotFoundException("Villa not found with number: " + number));
		existing.setRoadNumber(villa.getRoadNumber());
		existing.setOwnerName(villa.getOwnerName());
		existing.setOwnerEmail(villa.getOwnerEmail());
		existing.setContactNumber(villa.getContactNumber());
		existing.setIsOccupied(villa.getIsOccupied());
		return villaRepository.save(existing);
	}

	@Override
	public void deleteVillaDetails(String number) {
		Villa existing = villaRepository.findByVillaNumber(number)
			.orElseThrow(() -> new ResourceNotFoundException("Villa not found with id: " + number));
		villaRepository.delete(existing);
	}

}
