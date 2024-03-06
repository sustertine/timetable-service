package com.suster.timetableservice.timetable.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimetableEntryResponseDto {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
