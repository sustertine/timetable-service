package com.suster.timetableservice.timetable.dao;

import com.suster.timetableservice.timetable.vao.TimetableEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimetableEntryRepository extends JpaRepository<TimetableEntry, Long> {
}
