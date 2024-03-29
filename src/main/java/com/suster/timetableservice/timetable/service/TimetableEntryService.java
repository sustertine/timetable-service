package com.suster.timetableservice.timetable.service;

import com.suster.timetableservice.shared.exception.ResourceNotFoundException;
import com.suster.timetableservice.timetable.dao.TimetableEntryRepository;
import com.suster.timetableservice.timetable.dto.TimetableEntryRequestDto;
import com.suster.timetableservice.timetable.dto.TimetableEntryResponseDto;
import com.suster.timetableservice.timetable.vao.TimetableEntry;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TimetableEntryService {
    private final TimetableEntryRepository timetableEntryRepository;
    private final ModelMapper modelMapper;
    private final TimetableEntryPreconditionService timetableEntryPreconditionService;
    public TimetableEntryResponseDto findById(Long id) {
        log.info("Finding timetable entry by id: {}", id);
        return modelMapper.map(
                timetableEntryRepository.findById(id).orElseThrow(
                        () -> new ResourceNotFoundException("Timetable entry not found with id: " + id)
                ),
                TimetableEntryResponseDto.class
        );
    }

    @Transactional
    public void update(Long id, TimetableEntryRequestDto timetableEntryRequestDto) {
        timetableEntryPreconditionService.validate(timetableEntryRequestDto);

        TimetableEntry timetableEntry = timetableEntryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Timetable entry not found with id: " + id)
        );

        log.info("Updating timetable entry with id: {},  dto: {}", id, timetableEntryRequestDto);

        timetableEntry.setTitle(timetableEntryRequestDto.getTitle());
        timetableEntry.setDescription(timetableEntryRequestDto.getDescription());
        timetableEntry.setStartTime(timetableEntryRequestDto.getStartTime());
        timetableEntry.setEndTime(timetableEntryRequestDto.getEndTime());
        timetableEntryRepository.save(timetableEntry);
    }
}
