package com.ev.charging.system.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ev.charging.system.entity.Reservation;
import com.ev.charging.system.service.ReservationService;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

	private final ReservationService reservationService;

	@Autowired
	public ReservationController(ReservationService reservationService) {
		this.reservationService = reservationService;
	}

	// Get reservations by user
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Reservation>> getReservationsByUser(@PathVariable Long userId) {
		return ResponseEntity.ok(reservationService.getReservationsByUser(userId));
	}

	// Get reservations by charging station
	@GetMapping("/station/{stationId}")
	public ResponseEntity<List<Reservation>> getReservationsByChargingStation(@PathVariable Long stationId) {
		return ResponseEntity.ok(reservationService.getReservationsByChargingStation(stationId));
	}

	// Create a reservation
	@PostMapping("/station/{stationId}/user/{userId}")
	public ResponseEntity<Reservation> createReservation(@PathVariable Long stationId, @PathVariable Long userId,
			@RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime) {
		LocalDateTime start = LocalDateTime.parse(startTime);
		LocalDateTime end = LocalDateTime.parse(endTime);
		Reservation reservation = reservationService.createReservation(stationId, userId, start, end);
		return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
	}

	// Update reservation status (e.g., CONFIRMED)
	@PutMapping("/{reservationId}/status")
	public ResponseEntity<Reservation> updateReservationStatus(@PathVariable Long reservationId,
			@RequestParam Reservation.ReservationStatus status) {
		Reservation updatedReservation = reservationService.updateReservationStatus(reservationId, status);
		return ResponseEntity.ok(updatedReservation);
	}

	// Cancel reservation
	@DeleteMapping("/{reservationId}")
	public ResponseEntity<Void> cancelReservation(@PathVariable Long reservationId) {
		reservationService.cancelReservation(reservationId);
		return ResponseEntity.noContent().build();
	}
}
