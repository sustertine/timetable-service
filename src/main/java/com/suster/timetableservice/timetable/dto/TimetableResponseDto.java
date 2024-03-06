package com.suster.timetableservice.timetable.dto;

import lombok.Data;

import java.util.List;

@Data
public class TimetableResponseDto {
private Long id;
    private String title;
    private String description;
    private Long groupId;
    private List<TimetableEntryResponseDto> entries;
}
