package com.suster.timetableservice.timetable.rest;

import com.suster.timetableservice.timetable.dto.TimetableEntryRequestDto;
import com.suster.timetableservice.timetable.dto.TimetableEntryResponseDto;
import com.suster.timetableservice.timetable.dto.TimetableRequestDto;
import com.suster.timetableservice.timetable.dto.TimetableResponseDto;
import com.suster.timetableservice.timetable.service.TimetableService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/timetables")
@Tag(name = "Timetable controller", description = "API for timetable operations")
public class TimetableController {

    private final TimetableService timetableService;

    @PostMapping
    @Operation(summary = "Create a new timetable", description = "Create a new timetable and return its ID")
    public Long create(@RequestBody TimetableRequestDto timetableRequestDto) {
        log.info("POST /api/timetables");
        return timetableService.create(timetableRequestDto);
    }

    @GetMapping
    @Operation(summary = "Get all timetables", description = "Return a list of all timetables")
    public List<TimetableResponseDto> findAll() {
        log.info("GET /api/timetables");
        return timetableService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a timetable by ID", description = "Return a specific timetable by its ID")
    public TimetableResponseDto findById(@PathVariable Long id) {
        log.info("GET /api/timetables/{}", id);
        return timetableService.findById(id);
    }

    @GetMapping("/{id}/entries")
    @Operation(summary = "Get all entries for a timetable", description = "Return a list of all entries for a specific timetable")
    public List<TimetableEntryResponseDto> findEntriesById(@PathVariable Long id) {
        log.info("GET /api/timetables/{}/entries", id);
        return timetableService.findEntriesById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a timetable", description = "Update a specific timetable by its ID")
    public void update(@PathVariable Long id, @RequestBody TimetableRequestDto timetableRequestDto) {
        log.info("PUT /api/timetables/{}", id);
        timetableService.update(id, timetableRequestDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a timetable", description = "Delete a specific timetable by its ID")
    public void delete(@PathVariable Long id) {
        log.info("DELETE /api/timetables/{}", id);
        timetableService.delete(id);
    }

    @PostMapping("/{id}/entries")
    @Operation(summary = "Add an entry to a timetable", description = "Add an entry to a specific timetable by its ID")
    public void addEntry(@PathVariable Long id, @RequestBody TimetableEntryRequestDto timetableEntryRequestDto) {
        log.info("POST /api/timetables/{}/entries", id);
        timetableService.addEntry(id, timetableEntryRequestDto);
    }

    @DeleteMapping("/{id}/entries/{entryId}")
    @Operation(summary = "Delete an entry from a timetable", description = "Delete an entry from a specific timetable by its ID")
    public void deleteEntry(@PathVariable Long id, @PathVariable Long entryId) {
        log.info("DELETE /api/timetables/{}/entries/{}", id, entryId);
        timetableService.deleteEntry(id, entryId);
    }
}
