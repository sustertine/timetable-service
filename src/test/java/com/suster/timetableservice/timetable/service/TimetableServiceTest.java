package com.suster.timetableservice.timetable.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.suster.timetableservice.shared.exception.ResourceNotFoundException;
import com.suster.timetableservice.timetable.dao.TimetableEntryRepository;
import com.suster.timetableservice.timetable.dao.TimetableRepository;
import com.suster.timetableservice.timetable.dto.TimetableRequestDto;
import com.suster.timetableservice.timetable.vao.Timetable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;

class TimetableServiceTest {

    @Mock
    private TimetableRepository timetableRepository;

    @Mock
    private TimetableEntryRepository timetableEntryRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TimetableService timetableService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Create timetable successfully")
    void createTimetableSuccessfully() {
        TimetableRequestDto timetableRequestDto = new TimetableRequestDto();
        Timetable timetable = new Timetable();
        timetable.setId(1L);
        when(modelMapper.map(timetableRequestDto, Timetable.class)).thenReturn(timetable);
        when(timetableRepository.save(timetable)).thenReturn(timetable);

        Long createdId = timetableService.create(timetableRequestDto);

        assertNotNull(createdId);
    }

    @Test
    @DisplayName("Find all timetables successfully")
    void findAllTimetablesSuccessfully() {
        timetableService.findAll();

        verify(timetableRepository).findAll();
    }

    @Test
    @DisplayName("Find timetable by id successfully")
    void findTimetableByIdSuccessfully() {
        Long id = 1L;
        Timetable timetable = new Timetable();
        when(timetableRepository.findById(id)).thenReturn(Optional.of(timetable));

        timetableService.findById(id);

        verify(timetableRepository).findById(id);
    }

    @Test
    @DisplayName("Find timetable by id not found")
    void findTimetableByIdNotFound() {
        Long id = 1L;
        when(timetableRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> timetableService.findById(id));
    }

    @Test
    @DisplayName("Find timetable entries by id successfully")
    void updateTimetableSuccessfully() {
        Long id = 1L;
        TimetableRequestDto timetableRequestDto = new TimetableRequestDto();
        Timetable timetable = new Timetable();
        when(timetableRepository.findById(id)).thenReturn(Optional.of(timetable));

        timetableService.update(id, timetableRequestDto);

        verify(timetableRepository).save(timetable);
    }

    @Test
    @DisplayName("Update timetable not found")
    void deleteTimetableSuccessfully() {
        Long id = 1L;
        Timetable timetable = new Timetable();
        when(timetableRepository.findById(id)).thenReturn(Optional.of(timetable));

        timetableService.delete(id);

        verify(timetableRepository).delete(timetable);
    }

    @Test
    @DisplayName("Delete timetable not found")
    void deleteTimetableNotFound() {
        Long id = 1L;
        when(timetableRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> timetableService.delete(id));
    }
}