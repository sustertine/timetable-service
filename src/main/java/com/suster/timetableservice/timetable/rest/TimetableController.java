package com.suster.timetableservice.timetable.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/timetable")
@Tag(name = "Timetable", description = "The Timetable API")
public class TimetableController {
    @GetMapping
    public String getTimetable() {
        return "Hello World";
    }
}
