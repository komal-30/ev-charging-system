package com.ev.charging.system.test	;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ev.charging.system.entity.ChargingStation;
import com.ev.charging.system.exception.ResourceNotFoundException;
import com.ev.charging.system.repository.ChargingStationRepository;
import com.ev.charging.system.service.ChargingStationService;

class ChargingStationServiceTest {

	@Mock
	private ChargingStationRepository chargingStationRepository;

	@InjectMocks
	private ChargingStationService chargingStationService;

	private ChargingStation chargingStation;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		chargingStation = new ChargingStation();
		chargingStation.setId(1L);
		chargingStation.setName("EV Station 1");
		chargingStation.setLocation("Dublin");
		chargingStation.setAvailable(true);
		chargingStation.setTotalChargers(10);
	}

	@Test
	void testGetAllChargingStations() {
		List<ChargingStation> stationList = Arrays.asList(chargingStation);
		when(chargingStationRepository.findAll()).thenReturn(stationList);

		List<ChargingStation> result = chargingStationService.getAllChargingStations();

		assertEquals(1, result.size());
		verify(chargingStationRepository, times(1)).findAll();
	}

	@Test
	void testGetChargingStationById_WhenFound() {
		when(chargingStationRepository.findById(1L)).thenReturn(Optional.of(chargingStation));

		ChargingStation result = chargingStationService.getChargingStationById(1L);

		assertNotNull(result);
		assertEquals("EV Station 1", result.getName());
	}

	@Test
	void testGetChargingStationById_WhenNotFound() {
		when(chargingStationRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> chargingStationService.getChargingStationById(1L));
	}

	@Test
	void testCreateChargingStation() {
		when(chargingStationRepository.save(chargingStation)).thenReturn(chargingStation);

		ChargingStation result = chargingStationService.createChargingStation(chargingStation);

		assertEquals("EV Station 1", result.getName());
		verify(chargingStationRepository).save(chargingStation);
	}

	@Test
	void testUpdateChargingStation() {
		ChargingStation updated = new ChargingStation();
		updated.setName("Updated EV Station");
		updated.setLocation("Cork");
		updated.setAvailable(false);
		updated.setTotalChargers(5);

		when(chargingStationRepository.findById(1L)).thenReturn(Optional.of(chargingStation));
		when(chargingStationRepository.save(any(ChargingStation.class))).thenReturn(updated);

		ChargingStation result = chargingStationService.updateChargingStation(1L, updated);

		assertEquals("Updated EV Station", result.getName());
		assertEquals("Cork", result.getLocation());
		assertFalse(result.isAvailable());
		assertEquals(5, result.getTotalChargers());
	}

	@Test
	void testDeleteChargingStation() {
		when(chargingStationRepository.findById(1L)).thenReturn(Optional.of(chargingStation));

		chargingStationService.deleteChargingStation(1L);

		verify(chargingStationRepository).delete(chargingStation);
	}
}
