package com.ev.charging.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ev.charging.system.entity.ChargingStation;
import com.ev.charging.system.service.ChargingStationService;

@RestController
@RequestMapping("/api/charging-stations")
public class ChargingStationController {

	private final ChargingStationService chargingStationService;

	@Autowired
	public ChargingStationController(ChargingStationService chargingStationService) {
		this.chargingStationService = chargingStationService;
	}

	// Get all charging stations
	@GetMapping
	public ResponseEntity<List<ChargingStation>> getAllChargingStations() {
		return ResponseEntity.ok(chargingStationService.getAllChargingStations());
	}

	// Get a charging station by ID
	@GetMapping("/{id}")
	public ResponseEntity<ChargingStation> getChargingStationById(@PathVariable Long id) {
		return ResponseEntity.ok(chargingStationService.getChargingStationById(id));
	}

	// Create a new charging station
	@PostMapping
	public ResponseEntity<ChargingStation> createChargingStation(@RequestBody ChargingStation chargingStation) {
		ChargingStation createdStation = chargingStationService.createChargingStation(chargingStation);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdStation);
	}

	// Update an existing charging station
	@PutMapping("/{id}")
	public ResponseEntity<ChargingStation> updateChargingStation(@PathVariable Long id,
			@RequestBody ChargingStation chargingStationDetails) {
		ChargingStation updatedStation = chargingStationService.updateChargingStation(id, chargingStationDetails);
		return ResponseEntity.ok(updatedStation);
	}

	// Delete a charging station
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteChargingStation(@PathVariable Long id) {
		chargingStationService.deleteChargingStation(id);
		return ResponseEntity.noContent().build();
	}
}
	