package com.suster.timetableservice.timetable.vao;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
@Table
public class TimetableEntry {
    @Id
    @SequenceGenerator(name = "timetable_entry_id_seq", sequenceName = "timetable_entry_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "timetable_entry_id_seq")
    private Long id;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
