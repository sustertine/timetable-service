package com.suster.timetableservice.timetable.service;

import com.suster.timetableservice.timetable.dto.TimetableEntryRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TimetableEntryPreconditionService {
    public void validate(TimetableEntryRequestDto timetableEntryRequestDto) {
        log.info("Validating timetable entry: {}", timetableEntryRequestDto);
        if (timetableEntryRequestDto.getStartTime().isAfter(timetableEntryRequestDto.getEndTime())) {
            log.error("Start time cannot be after end time. Start time: {}, end time: {}",
                    timetableEntryRequestDto.getStartTime(), timetableEntryRequestDto.getEndTime());
            throw new IllegalArgumentException("Start time cannot be after end time");
        }
    }
}
