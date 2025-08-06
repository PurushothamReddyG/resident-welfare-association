package com.ssc.rwa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssc.rwa.model.Villa;
import com.ssc.rwa.serviceImpl.VillaServiceImpl;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
@Validated
public class VillaController {

	@Autowired
	private VillaServiceImpl villaServiceImpl;

	@GetMapping("get-all")
	public ResponseEntity<List<Villa>> getAllVillas() {
		return ResponseEntity.ok(villaServiceImpl.getAllVillas());
	}

	@GetMapping("/get/{number}")
	public ResponseEntity<Villa> getVillaDetailsByNumber(@PathVariable String number) {
		return ResponseEntity.ok(villaServiceImpl.getVillaDetailsByNumber(number));
	}

	@PostMapping("add-villa-details")
	public ResponseEntity<Villa> addNewVillaDetails(@RequestBody @Valid Villa villa) {
		return new ResponseEntity<>(villaServiceImpl.addNewVillaDetails(villa), HttpStatus.CREATED);
	}

	@PutMapping("/update/{number}")
	public ResponseEntity<Villa> updateVillaDetails(@PathVariable String number, @RequestBody @Valid Villa villa) {
		return ResponseEntity.ok(villaServiceImpl.updateVilla(number, villa));
	}

	@DeleteMapping("/delete/{number}")
	public ResponseEntity<Void> deleteVilla(@PathVariable String number) {
		villaServiceImpl.deleteVillaDetails(number);
		return ResponseEntity.noContent().build();
	}

}
