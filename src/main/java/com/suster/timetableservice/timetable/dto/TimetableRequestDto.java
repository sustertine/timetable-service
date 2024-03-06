package com.suster.timetableservice.timetable.dto;

import lombok.Data;

@Data
public class TimetableRequestDto {
    private String title;
    private String description;
    private Long groupId;
}
