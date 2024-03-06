package com.suster.timetableservice.timetable.dao;

import com.suster.timetableservice.timetable.vao.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimetableRepository extends JpaRepository<Timetable, Long> {
}
