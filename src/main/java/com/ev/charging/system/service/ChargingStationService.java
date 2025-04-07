package com.ev.charging.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ev.charging.system.entity.ChargingStation;
import com.ev.charging.system.exception.ResourceNotFoundException;
import com.ev.charging.system.repository.ChargingStationRepository;

@Service
public class ChargingStationService {

	private final ChargingStationRepository chargingStationRepository;

	@Autowired
	public ChargingStationService(ChargingStationRepository chargingStationRepository) {
		this.chargingStationRepository = chargingStationRepository;
	}

	// Get all charging stations
	public List<ChargingStation> getAllChargingStations() {
		return chargingStationRepository.findAll();
	}

	// Get charging station by ID
	public ChargingStation getChargingStationById(Long id) {
		return chargingStationRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Charging Station not found"));
	}

	// Create new charging station
	public ChargingStation createChargingStation(ChargingStation chargingStation) {
		return chargingStationRepository.save(chargingStation);
	}

	// Update charging station details
	public ChargingStation updateChargingStation(Long id, ChargingStation chargingStationDetails) {
		ChargingStation chargingStation = getChargingStationById(id);
		chargingStation.setName(chargingStationDetails.getName());
		chargingStation.setLocation(chargingStationDetails.getLocation());
		chargingStation.setAvailable(chargingStationDetails.isAvailable());
		chargingStation.setTotalChargers(chargingStationDetails.getTotalChargers());
		return chargingStationRepository.save(chargingStation);
	}

	// Delete charging station
	public void deleteChargingStation(Long id) {
		ChargingStation chargingStation = getChargingStationById(id);
		chargingStationRepository.delete(chargingStation);
	}
}
