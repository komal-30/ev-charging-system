package com.ev.charging.system.test;

import static org.hamcrest.CoreMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc; // Added import
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.ev.charging.system.controller.ReservationController;
import com.ev.charging.system.entity.Reservation;
import com.ev.charging.system.service.ReservationService;

@SpringBootTest // Loads the full application context
@AutoConfigureMockMvc // Automatically configures MockMvc
@ExtendWith(SpringExtension.class)
public class ReservationControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ReservationController reservationController;

	@MockBean
	private ReservationService reservationService;

	@Test
	void testGetReservationsByUser() throws Exception {
		Reservation reservation = new Reservation();
		reservation.setId(1L);
		reservation.setUserId(1L);

		Mockito.when(reservationService.getReservationsByUser(1L)).thenReturn(List.of(reservation));

		mockMvc.perform(get("/api/reservations/user/1")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(1L));
	}

	@Test
	void testGetReservationsByChargingStation() throws Exception {
		Reservation reservation = new Reservation();
		reservation.setId(2L);

		Mockito.when(reservationService.getReservationsByChargingStation(5L)).thenReturn(List.of(reservation));

		mockMvc.perform(get("/api/reservations/station/5")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(2L));
	}

	@Test
	void testCreateReservation() throws Exception {
	    LocalDateTime start = LocalDateTime.now();
	    LocalDateTime end = start.plusHours(1);

	    Reservation reservation = new Reservation();
	    reservation.setId(10L);

	    // Modify the mock setup: Using any(LocalDateTime.class) instead of eq() with matchers
	    Mockito.when(reservationService.createReservation(eq(3L), eq(4L), Mockito.any(LocalDateTime.class),
	            Mockito.any(LocalDateTime.class))).thenReturn(reservation);

	    mockMvc.perform(post("/api/reservations/station/3/user/4")
	            .param("startTime", start.toString())
	            .param("endTime", end.toString())
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isCreated())
	            .andExpect(jsonPath("$.id").value(10L));
	}

	@Test
	void testUpdateReservationStatus() throws Exception {
		Reservation reservation = new Reservation();
		reservation.setId(7L);
		reservation.setStatus(Reservation.ReservationStatus.CONFIRMED);

		Mockito.when(reservationService.updateReservationStatus(7L, Reservation.ReservationStatus.CONFIRMED))
				.thenReturn(reservation);

		mockMvc.perform(put("/api/reservations/7/status").param("status", "CONFIRMED")).andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("CONFIRMED"));
	}

	@Test
	void testCancelReservation() throws Exception {
		Mockito.doNothing().when(reservationService).cancelReservation(9L);

		mockMvc.perform(delete("/api/reservations/9")).andExpect(status().isNoContent());
	}
}
