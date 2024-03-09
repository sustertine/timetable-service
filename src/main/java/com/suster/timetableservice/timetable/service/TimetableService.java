package com.suster.timetableservice.timetable.service;

import com.suster.timetableservice.shared.exception.ResourceNotFoundException;
import com.suster.timetableservice.timetable.dao.TimetableEntryRepository;
import com.suster.timetableservice.timetable.dao.TimetableRepository;
import com.suster.timetableservice.timetable.dto.TimetableEntryRequestDto;
import com.suster.timetableservice.timetable.dto.TimetableEntryResponseDto;
import com.suster.timetableservice.timetable.dto.TimetableRequestDto;
import com.suster.timetableservice.timetable.dto.TimetableResponseDto;
import com.suster.timetableservice.timetable.vao.Timetable;
import com.suster.timetableservice.timetable.vao.TimetableEntry;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TimetableService {
    private final TimetableRepository timetableRepository;
    private final TimetableEntryRepository timetableEntryRepository;
    private final TimetableEntryPreconditionService timetableEntryPreconditionService;
    private final ModelMapper modelMapper;

    @Transactional
    public Long create(TimetableRequestDto timetableRequestDto) {
        Timetable timetable = modelMapper.map(timetableRequestDto, Timetable.class);
        log.info("Creating new timetable: {}", timetable);
        return timetableRepository.save(timetable).getId();
    }

    public List<TimetableResponseDto> findAll() {
        log.info("Fetching all timetables");
        return timetableRepository.findAll().stream()
                .map(timetable -> modelMapper.map(timetable, TimetableResponseDto.class))
                .toList();
    }

    public TimetableResponseDto findById(Long id) {
        Timetable timetable = timetableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Timetable not found with id: " + id));

        log.info("Fetching timetable by id: {}", id);

        return modelMapper.map(timetable, TimetableResponseDto.class);
    }

    public List<TimetableEntryResponseDto> findEntriesById(Long id) {
        Timetable timetable = timetableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Timetable not found with id: " + id));

        log.info("Fetching entries for timetable with id: {}", id);

        return timetable.getEntries().stream()
                .map(entry -> modelMapper.map(entry, TimetableEntryResponseDto.class))
                .toList();
    }

    @Transactional
    public void update(Long id, TimetableRequestDto timetableRequestDto) {
        Timetable timetable = timetableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Timetable not found with id: " + id));

        log.info("Updating timetable with id: {}, dto: {}", id, timetableRequestDto);

        timetable.setTitle(timetableRequestDto.getTitle());
        timetable.setDescription(timetableRequestDto.getDescription());
        timetable.setGroupId(timetableRequestDto.getGroupId());

        timetableRepository.save(timetable);
    }

    @Transactional
    public void delete(Long id) {
        Timetable timetable = timetableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Timetable not found with id: " + id));

        log.info("Deleting timetable with id: {}", id);

        timetableRepository.delete(timetable);
    }

    @Transactional
    public void addEntry(Long id, TimetableEntryRequestDto timetableEntryRequestDto) {
        timetableEntryPreconditionService.validate(timetableEntryRequestDto);

        Timetable timetable = timetableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Timetable not found with id: " + id));

        if (timetableEntryRequestDto.getRecurring() != null && timetableEntryRequestDto.getRecurring()) {
            // TODO: Implement recurring entries
            log.error("Recurring entries are not supported yet. dto: {}", timetableEntryRequestDto);
            throw new UnsupportedOperationException("Recurring entries are not supported yet");
        }

        log.info("Adding entry to timetable with id: {}, entry: {}", id, timetableEntryRequestDto);

        TimetableEntry entry = timetableEntryRepository.save(modelMapper.map(timetableEntryRequestDto, TimetableEntry.class));
        timetable.getEntries().add(entry);
        timetableRepository.save(timetable);
    }

    @Transactional
    public void deleteEntry(Long id, Long entryId) {
        Timetable timetable = timetableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Timetable not found with id: " + id));

        TimetableEntry entry = timetable.getEntries().stream()
                .filter(e -> e.getId().equals(entryId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Timetable entry not found with id: " + entryId));

        log.info("Deleting entry from timetable with id: {}, entry id: {}", id, entryId);

        timetable.getEntries().remove(entry);
        timetableEntryRepository.delete(entry);
        timetableRepository.save(timetable);
    }
}