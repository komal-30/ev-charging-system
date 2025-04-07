package com.ev.charging.system.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "")
@NoArgsConstructor // ← Add this (required by Hibernate)
@AllArgsConstructor // ← Optional but recommended
public class Reservation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "charging_station_id")
    private ChargingStation chargingStation;

    private Long userId; // Assuming user ID will be passed; can be mapped to a User entity if needed.
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private ReservationStatus status;

    public enum ReservationStatus {
        PENDING, CONFIRMED, CANCELLED
    }

	public Long getId() {
		return id;
	}

	public ChargingStation getChargingStation() {
		return chargingStation;
	}

	public Long getUserId() {
		return userId;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public ReservationStatus getStatus() {
		return status;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setChargingStation(ChargingStation chargingStation) {
		this.chargingStation = chargingStation;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public void setStatus(ReservationStatus status) {
		this.status = status;
	}
    
    

}