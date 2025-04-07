package com.ev.charging.system.test;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.ev.charging.system.controller.ChargingStationController;
import com.ev.charging.system.entity.ChargingStation;
import com.ev.charging.system.service.ChargingStationService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class ChargingStationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ChargingStationController chargingStationController;

    @MockBean
    private ChargingStationService chargingStationService;

    @Test
    void testGetAllChargingStations() throws Exception {
        ChargingStation chargingStation = new ChargingStation();
        chargingStation.setId(1L);
        chargingStation.setName("Station 1");

        Mockito.when(chargingStationService.getAllChargingStations()).thenReturn(List.of(chargingStation));

        mockMvc.perform(get("/api/charging-stations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Station 1"));
    }

    @Test
    void testGetChargingStationById() throws Exception {
        ChargingStation chargingStation = new ChargingStation();
        chargingStation.setId(1L);
        chargingStation.setName("Station 1");

        Mockito.when(chargingStationService.getChargingStationById(1L)).thenReturn(chargingStation);

        mockMvc.perform(get("/api/charging-stations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Station 1"));
    }

    @Test
    void testCreateChargingStation() throws Exception {
        ChargingStation chargingStation = new ChargingStation();
        chargingStation.setId(1L);
        chargingStation.setName("New Station");

        Mockito.when(chargingStationService.createChargingStation(Mockito.any(ChargingStation.class)))
                .thenReturn(chargingStation);

        mockMvc.perform(post("/api/charging-stations")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"New Station\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("New Station"));
    }

    @Test
    void testUpdateChargingStation() throws Exception {
        ChargingStation chargingStation = new ChargingStation();
        chargingStation.setId(1L);
        chargingStation.setName("Updated Station");

        Mockito.when(chargingStationService.updateChargingStation(eq(1L), Mockito.any(ChargingStation.class)))
                .thenReturn(chargingStation);

        mockMvc.perform(put("/api/charging-stations/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Updated Station\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Updated Station"));
    }

    @Test
    void testDeleteChargingStation() throws Exception {
        Mockito.doNothing().when(chargingStationService).deleteChargingStation(1L);

        mockMvc.perform(delete("/api/charging-stations/1"))
                .andExpect(status().isNoContent());
    }
}
