package com.suster.timetableservice.timetable.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suster.timetableservice.shared.exception.ResourceNotFoundException;
import com.suster.timetableservice.timetable.dto.TimetableEntryRequestDto;
import com.suster.timetableservice.timetable.dto.TimetableEntryResponseDto;
import com.suster.timetableservice.timetable.dto.TimetableRequestDto;
import com.suster.timetableservice.timetable.dto.TimetableResponseDto;
import com.suster.timetableservice.timetable.service.TimetableService;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TimetableController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class TimetableControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TimetableService timetableService;

    private TimetableRequestDto timetableRequestDto;
    private TimetableResponseDto timetableResponseDto1;
    private TimetableResponseDto timetableResponseDto2;
    private List<TimetableEntryResponseDto> timetableEntryResponseDtos = new ArrayList<>();
    private TimetableEntryRequestDto timetableEntryRequestDto;
    private final LocalDateTime startTime = LocalDateTime.parse("2021-12-12T12:00:00");
    private final LocalDateTime endTime = LocalDateTime.parse("2021-12-12T13:00:00");
    @BeforeEach
    void setup() {
        timetableEntryResponseDtos.add(new TimetableEntryResponseDto(1L, "title", "description", startTime, endTime));
        timetableEntryResponseDtos.add(new TimetableEntryResponseDto(2L, "title", "description", startTime, endTime));

        timetableRequestDto = new TimetableRequestDto("title", "description", 1L);
        timetableResponseDto1 = new TimetableResponseDto(1L, "title", "description", 1L, timetableEntryResponseDtos);
        timetableResponseDto2 = new TimetableResponseDto(2L, "title", "description", 1L, timetableEntryResponseDtos);
        timetableEntryRequestDto = new TimetableEntryRequestDto("title", "description", startTime, endTime, false);
    }


    @Test
    @DisplayName("Create timetable successful")
    void creatSuccess() throws Exception {
        when(timetableService.create(any(TimetableRequestDto.class))).thenReturn(1L);

        mockMvc.perform(post("/api/timetables")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(timetableRequestDto))
                )
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(1L))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("find all timetables successful")
    void findAllSuccess() throws Exception {
        when(timetableService.findAll()).thenReturn(List.of(timetableResponseDto1, timetableResponseDto2));
        mockMvc.perform(get("/api/timetables")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(timetableRequestDto))
                )
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].groupId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].entries").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].description").value("description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].groupId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].entries").isNotEmpty())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("find all timetables empty")
    void findAllEmpty() throws Exception {
        when(timetableService.findAll()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/api/timetables")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(timetableRequestDto))
                )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("find by id successful")
    void findByIdSuccess() throws Exception {
        when(timetableService.findById(anyLong())).thenReturn(timetableResponseDto1);
        mockMvc.perform(get("/api/timetables/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(timetableRequestDto))
                )
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.groupId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.entries").isNotEmpty())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("find by id timetable not found")
    void findByIdNotFound() throws Exception {
        doThrow(ResourceNotFoundException.class).when(timetableService).findById(anyLong());
        mockMvc.perform(get("/api/timetables/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(timetableRequestDto))
                )
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("find entries by id successful")
    void findEntriesByIdSuccess() throws Exception {
        when(timetableService.findEntriesById(anyLong())).thenReturn(timetableEntryResponseDtos);
        mockMvc.perform(get("/api/timetables/1/entries")
                        .contentType(MediaType.APPLICATION_JSON)

                )
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].startTime").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endTime").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].description").value("description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].startTime").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endTime").isNotEmpty())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("update successful")
    void updateSuccessful() throws Exception {
        mockMvc.perform(put("/api/timetables/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(timetableRequestDto))
                )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("update entry not found")
    void updateNotFound() throws Exception {
        doThrow(ResourceNotFoundException.class).when(timetableService).update(anyLong(), any(TimetableRequestDto.class));

        mockMvc.perform(put("/api/timetables/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(timetableRequestDto))
                )
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("delete successful")
    void deleteSuccessful() throws Exception {
        mockMvc.perform(delete("/api/timetables/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("delete timetable not found")
    void deleteNotFound() throws Exception {
        doThrow(ResourceNotFoundException.class).when(timetableService).delete(anyLong());
        mockMvc.perform(delete("/api/timetables/1"))
                .andExpect(status().isNotFound());
    }


    @Test
    @DisplayName("add entry successful")
    void addEntrySuccessful() throws Exception {
        mockMvc.perform(post("/api/timetables/1/entries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(timetableRequestDto))
                )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("add entry, timetable not found")
    void addEntryNotFound() throws Exception {
        doThrow(ResourceNotFoundException.class).when(timetableService).addEntry(anyLong(), any(TimetableEntryRequestDto.class));
        mockMvc.perform(post("/api/timetables/1/entries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(timetableRequestDto))
                )
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("delete entry successful")
    void deleteEntrySuccessful() throws Exception {
        mockMvc.perform(delete("/api/timetables/1/entries/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("delete entry, entry not found")
    void deleteEntryNotFound() throws Exception {
        doThrow(ResourceNotFoundException.class).when(timetableService).deleteEntry(anyLong(), anyLong());

        mockMvc.perform(delete("/api/timetables/1/entries/1"))
                .andExpect(status().isNotFound());
    }
}