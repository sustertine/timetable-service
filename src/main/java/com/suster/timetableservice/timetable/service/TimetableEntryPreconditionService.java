package com.suster.timetableservice.timetable.service;

import com.suster.timetableservice.timetable.dto.TimetableEntryRequestDto;
import org.springframework.stereotype.Service;

@Service
public class TimetableEntryPreconditionService {
    public void validate(TimetableEntryRequestDto timetableEntryRequestDto) {
        if (timetableEntryRequestDto.getStartTime().isAfter(timetableEntryRequestDto.getEndTime())) {
            throw new IllegalArgumentException("Start time cannot be after end time");
        }
    }
}
