package com.suster.timetableservice.timetable.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimetableResponseDto {
    private Long id;
    private String title;
    private String description;
    private Long groupId;
    private List<TimetableEntryResponseDto> entries;
}
