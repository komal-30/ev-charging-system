package com.ev.charging.system.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ev.charging.system.entity.ChargingStation;
import com.ev.charging.system.entity.Reservation;
import com.ev.charging.system.exception.ResourceNotFoundException;
import com.ev.charging.system.repository.ChargingStationRepository;
import com.ev.charging.system.repository.ReservationRepository;

@Service
public class ReservationService {

	private final ReservationRepository reservationRepository;
	private final ChargingStationRepository chargingStationRepository;

	@Autowired
	public ReservationService(ReservationRepository reservationRepository,
			ChargingStationRepository chargingStationRepository) {
		this.reservationRepository = reservationRepository;
		this.chargingStationRepository = chargingStationRepository;
	}

	// Get all reservations for a user
	public List<Reservation> getReservationsByUser(Long userId) {
		return reservationRepository.findByUserId(userId);
	}

	// Get all reservations for a charging station
	public List<Reservation> getReservationsByChargingStation(Long stationId) {
		return reservationRepository.findByChargingStationId(stationId);
	}

	// Create a new reservation
	public Reservation createReservation(Long stationId, Long userId, LocalDateTime startTime, LocalDateTime endTime) {
		ChargingStation chargingStation = chargingStationRepository.findById(stationId)
				.orElseThrow(() -> new ResourceNotFoundException("Charging Station not found"));
		Reservation reservation = new Reservation();
		reservation.setChargingStation(chargingStation);
		reservation.setUserId(userId);
		reservation.setStartTime(startTime);
		reservation.setEndTime(endTime);
		reservation.setStatus(Reservation.ReservationStatus.PENDING);
		return reservationRepository.save(reservation);
	}

	// Update reservation status (e.g., from PENDING to CONFIRMED)
	public Reservation updateReservationStatus(Long reservationId, Reservation.ReservationStatus status) {
		Reservation reservation = reservationRepository.findById(reservationId)
				.orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
		reservation.setStatus(status);
		return reservationRepository.save(reservation);
	}

	// Cancel a reservation
	public void cancelReservation(Long reservationId) {
		Reservation reservation = reservationRepository.findById(reservationId)
				.orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
		reservation.setStatus(Reservation.ReservationStatus.CANCELLED);
		reservationRepository.save(reservation);
	}
}
