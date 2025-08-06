package com.ssc.rwa.service;

import java.util.List;

import com.ssc.rwa.model.Villa;

public interface VillaService {

	List<Villa> getAllVillas();

	Villa getVillaDetailsByNumber(String number);

	Villa addNewVillaDetails(Villa villa);

	Villa updateVilla(String number, Villa villa);

	void deleteVillaDetails(String number);

}
