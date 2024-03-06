package com.suster.timetableservice.timetable.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suster.timetableservice.shared.exception.ResourceNotFoundException;
import com.suster.timetableservice.timetable.dto.TimetableEntryRequestDto;
import com.suster.timetableservice.timetable.dto.TimetableEntryResponseDto;
import com.suster.timetableservice.timetable.service.TimetableEntryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TimetableEntryController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class TimetableEntryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TimetableEntryService timetableEntryService;

    private TimetableEntryResponseDto timetableEntryResponseDto;
    private TimetableEntryRequestDto timetableEntryRequestDto;

    @BeforeEach
    void setup() {
        timetableEntryResponseDto = new TimetableEntryResponseDto(1L, "title", "description", LocalDateTime.now(), LocalDateTime.now());
        timetableEntryRequestDto = new TimetableEntryRequestDto("title", "description", LocalDateTime.now(), LocalDateTime.now(), false);
    }

    @Test
    @DisplayName("findById returns entry")
    public void findByIdReturnsEntry() throws Exception {
        when(timetableEntryService.findById(1L)).thenReturn(timetableEntryResponseDto);

        mockMvc.perform(get("/api/timetable-entries/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(timetableEntryResponseDto))
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startTime").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.endTime").isNotEmpty());

    }

    @Test
    @DisplayName("findById throws ResourceNotFoundException")
    public void findByIdThrowsResourceNotFoundException() throws Exception {
        doThrow(ResourceNotFoundException.class).when(timetableEntryService).findById(1L);

        mockMvc.perform(get("/api/timetable-entries/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    @DisplayName("update successfully")
    void updateSuccessfully() throws Exception {
        mockMvc.perform(put("/api/timetable-entries/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(timetableEntryRequestDto)))
                .andExpect(status().isOk());

        verify(timetableEntryService).update(1L, timetableEntryRequestDto);
    }

    @Test
    @DisplayName("update with invalid id")
    void updateWithInvalidId() throws Exception {
        doThrow(ResourceNotFoundException.class).when(timetableEntryService).update(0L, timetableEntryRequestDto);

        mockMvc.perform(put("/api/timetable-entries/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(timetableEntryRequestDto)))
                .andExpect(status().isNotFound());
    }
}