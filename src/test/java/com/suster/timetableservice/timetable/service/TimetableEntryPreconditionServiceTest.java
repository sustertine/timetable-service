package com.suster.timetableservice.timetable.service;

import com.suster.timetableservice.timetable.dto.TimetableEntryRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TimetableEntryPreconditionServiceTest {

    private TimetableEntryPreconditionService timetableEntryPreconditionService;

    @BeforeEach
    public void setup() {
        timetableEntryPreconditionService = new TimetableEntryPreconditionService();
    }

    @Test
    @DisplayName("validate throws exception when start time is after end time")
    public void validateThrowsExceptionWhenStartTimeIsAfterEndTime() {
        TimetableEntryRequestDto timetableEntryRequestDto = new TimetableEntryRequestDto();
        timetableEntryRequestDto.setStartTime(LocalDateTime.now().plusHours(1));
        timetableEntryRequestDto.setEndTime(LocalDateTime.now());

        assertThrows(IllegalArgumentException.class, () -> timetableEntryPreconditionService.validate(timetableEntryRequestDto));
    }

    @Test
    @DisplayName("validate does not throw exception when start time is before end time")
    public void validateDoesNotThrowExceptionWhenStartTimeIsBeforeEndTime() {
        TimetableEntryRequestDto timetableEntryRequestDto = new TimetableEntryRequestDto();
        timetableEntryRequestDto.setStartTime(LocalDateTime.now());
        timetableEntryRequestDto.setEndTime(LocalDateTime.now().plusHours(1));

        timetableEntryPreconditionService.validate(timetableEntryRequestDto);
    }
}