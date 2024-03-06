package com.suster.timetableservice.timetable.rest;

import com.suster.timetableservice.timetable.dto.TimetableEntryRequestDto;
import com.suster.timetableservice.timetable.dto.TimetableEntryResponseDto;
import com.suster.timetableservice.timetable.service.TimetableEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/timetable-entries")
@RequiredArgsConstructor
public class TimetableEntryController {
    private final TimetableEntryService timetableEntryService;

    @GetMapping("/{id}")
    public TimetableEntryResponseDto findById(@PathVariable Long id) {
        return timetableEntryService.findById(id);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody TimetableEntryRequestDto timetableEntryRequestDto) {
        timetableEntryService.update(id, timetableEntryRequestDto);
    }
}
