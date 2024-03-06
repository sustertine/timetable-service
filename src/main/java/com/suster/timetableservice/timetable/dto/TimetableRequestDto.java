package com.suster.timetableservice.timetable.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimetableRequestDto {
    private String title;
    private String description;
    private Long groupId;
}
