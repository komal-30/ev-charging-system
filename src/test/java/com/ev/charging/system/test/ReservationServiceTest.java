package com.ev.charging.system.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ev.charging.system.entity.ChargingStation;
import com.ev.charging.system.entity.Reservation;
import com.ev.charging.system.entity.Reservation.ReservationStatus;
import com.ev.charging.system.exception.ResourceNotFoundException;
import com.ev.charging.system.repository.ChargingStationRepository;
import com.ev.charging.system.repository.ReservationRepository;
import com.ev.charging.system.service.ReservationService;

class ReservationServiceTest {

	@Mock
	private ReservationRepository reservationRepository;

	@Mock
	private ChargingStationRepository chargingStationRepository;

	@InjectMocks
	private ReservationService reservationService;

	private ChargingStation station;
	private Reservation reservation;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		station = new ChargingStation();
		station.setId(1L);
		station.setName("EV Station 1");

		reservation = new Reservation();
		reservation.setId(1L);
		reservation.setChargingStation(station);
		reservation.setUserId(100L);
		reservation.setStartTime(LocalDateTime.now());
		reservation.setEndTime(LocalDateTime.now().plusHours(1));
		reservation.setStatus(ReservationStatus.PENDING);
	}

	@Test
	void testGetReservationsByUser() {
		when(reservationRepository.findByUserId(100L)).thenReturn(List.of(reservation));

		List<Reservation> result = reservationService.getReservationsByUser(100L);

		assertEquals(1, result.size());
		verify(reservationRepository).findByUserId(100L);
	}

	@Test
	void testGetReservationsByChargingStation() {
		when(reservationRepository.findByChargingStationId(1L)).thenReturn(List.of(reservation));

		List<Reservation> result = reservationService.getReservationsByChargingStation(1L);

		assertEquals(1, result.size());
		verify(reservationRepository).findByChargingStationId(1L);
	}

	@Test
	void testCreateReservation_Success() {
		LocalDateTime start = LocalDateTime.now();
		LocalDateTime end = start.plusHours(1);

		when(chargingStationRepository.findById(1L)).thenReturn(Optional.of(station));
		when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

		Reservation result = reservationService.createReservation(1L, 100L, start, end);

		assertNotNull(result);
		assertEquals(ReservationStatus.PENDING, result.getStatus());
		assertEquals(100L, result.getUserId());
		verify(reservationRepository).save(any(Reservation.class));
	}

	@Test
	void testCreateReservation_StationNotFound() {
		when(chargingStationRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> reservationService.createReservation(1L, 100L,
				LocalDateTime.now(), LocalDateTime.now().plusHours(1)));
	}

	@Test
	void testUpdateReservationStatus_Success() {
		when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
		reservation.setStatus(ReservationStatus.CONFIRMED);
		when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

		Reservation result = reservationService.updateReservationStatus(1L, ReservationStatus.CONFIRMED);

		assertEquals(ReservationStatus.CONFIRMED, result.getStatus());
	}

	@Test
	void testUpdateReservationStatus_NotFound() {
		when(reservationRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class,
				() -> reservationService.updateReservationStatus(1L, ReservationStatus.CONFIRMED));
	}

	@Test
	void testCancelReservation_Success() {
		when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

		reservationService.cancelReservation(1L);

		assertEquals(ReservationStatus.CANCELLED, reservation.getStatus());
		verify(reservationRepository).save(reservation);
	}

	@Test
	void testCancelReservation_NotFound() {
		when(reservationRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> reservationService.cancelReservation(1L));
	}
}
