package com.suster.timetableservice.timetable.vao;

import lombok.*;

import java.time.LocalDateTime;
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TimetableEntry {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
