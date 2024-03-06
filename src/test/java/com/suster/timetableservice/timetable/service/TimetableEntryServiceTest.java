package com.suster.timetableservice.timetable.service;

import com.suster.timetableservice.shared.exception.ResourceNotFoundException;
import com.suster.timetableservice.timetable.dao.TimetableEntryRepository;
import com.suster.timetableservice.timetable.dto.TimetableEntryRequestDto;
import com.suster.timetableservice.timetable.dto.TimetableEntryResponseDto;
import com.suster.timetableservice.timetable.vao.TimetableEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TimetableEntryServiceTest {

    @Mock
    private TimetableEntryRepository timetableEntryRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private TimetableEntryPreconditionService timetableEntryPreconditionService;

    @InjectMocks
    private TimetableEntryService timetableEntryService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("findById returns timetable entry when it exists")
    public void findByIdReturnsTimetableEntryWhenExists() {
        TimetableEntry timetableEntry = new TimetableEntry();
        when(timetableEntryRepository.findById(any(Long.class))).thenReturn(java.util.Optional.of(timetableEntry));
        when(modelMapper.map(any(TimetableEntry.class), eq(TimetableEntryResponseDto.class))).thenReturn(new TimetableEntryResponseDto());

        timetableEntryService.findById(1L);

        verify(timetableEntryRepository, times(1)).findById(any(Long.class));
        verify(modelMapper, times(1)).map(any(TimetableEntry.class), eq(TimetableEntryResponseDto.class));
    }

    @Test
    @DisplayName("findById throws exception when timetable entry does not exist")
    public void findByIdThrowsExceptionWhenTimetableEntryDoesNotExist() {
        when(timetableEntryRepository.findById(any(Long.class))).thenReturn(java.util.Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> timetableEntryService.findById(1L));

        verify(timetableEntryRepository, times(1)).findById(any(Long.class));
    }

    @Test
    @DisplayName("update changes timetable entry details")
    public void updateChangesTimetableEntryDetails() {
        TimetableEntry timetableEntry = new TimetableEntry();
        when(timetableEntryRepository.findById(any(Long.class))).thenReturn(java.util.Optional.of(timetableEntry));
        when(timetableEntryRepository.save(any(TimetableEntry.class))).thenReturn(timetableEntry);

        TimetableEntryRequestDto timetableEntryRequestDto = new TimetableEntryRequestDto();
        timetableEntryRequestDto.setTitle("New Title");
        timetableEntryRequestDto.setDescription("New Description");
        timetableEntryRequestDto.setStartTime(LocalDateTime.now());
        timetableEntryRequestDto.setEndTime(LocalDateTime.now().plusHours(1));

        timetableEntryService.update(1L, timetableEntryRequestDto);

        verify(timetableEntryPreconditionService, times(1)).validate(any(TimetableEntryRequestDto.class));
        verify(timetableEntryRepository, times(1)).findById(any(Long.class));
        verify(timetableEntryRepository, times(1)).save(any(TimetableEntry.class));
    }

    @Test
    @DisplayName("update throws exception when timetable entry does not exist")
    public void updateThrowsExceptionWhenTimetableEntryDoesNotExist() {
        when(timetableEntryRepository.findById(any(Long.class))).thenReturn(java.util.Optional.empty());

        TimetableEntryRequestDto timetableEntryRequestDto = new TimetableEntryRequestDto();
        timetableEntryRequestDto.setTitle("New Title");
        timetableEntryRequestDto.setDescription("New Description");
        timetableEntryRequestDto.setStartTime(LocalDateTime.now());
        timetableEntryRequestDto.setEndTime(LocalDateTime.now().plusHours(1));

        assertThrows(ResourceNotFoundException.class, () -> timetableEntryService.update(1L, timetableEntryRequestDto));

        verify(timetableEntryPreconditionService, times(1)).validate(any(TimetableEntryRequestDto.class));
        verify(timetableEntryRepository, times(1)).findById(any(Long.class));
    }

}