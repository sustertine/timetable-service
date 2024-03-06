package com.suster.timetableservice.timetable.vao;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table
public class Timetable {

    @Id
    @SequenceGenerator(name = "timetable_id_seq", sequenceName = "timetable_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "timetable_id_seq")
    private Long id;
    private String title;
    private String description;
    private Long groupId;

    @OneToMany
    private List<TimetableEntry> entries;

    public void addEntry(TimetableEntry entry) {
        entries.add(entry);
    }
}
