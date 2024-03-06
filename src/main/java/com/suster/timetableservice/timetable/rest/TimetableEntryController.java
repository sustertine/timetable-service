package com.suster.timetableservice.timetable.rest;

import com.suster.timetableservice.timetable.dto.TimetableEntryRequestDto;
import com.suster.timetableservice.timetable.dto.TimetableEntryResponseDto;
import com.suster.timetableservice.timetable.service.TimetableEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/timetable-entries")
@RequiredArgsConstructor
public class TimetableEntryController {
    private final TimetableEntryService timetableEntryService;

    @GetMapping("/{id}")
    public TimetableEntryResponseDto findById(Long id) {
        return timetableEntryService.findById(id);
    }

    @PutMapping("/{id}")
    public void update(Long id, TimetableEntryRequestDto timetableEntryRequestDto) {
        timetableEntryService.update(id, timetableEntryRequestDto);
    }
}
