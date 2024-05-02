package ru.practicum.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.compilation.CompilationsDto;
import ru.practicum.dto.compilation.NewCompilationDto;
import ru.practicum.dto.compilation.UpdateCompilationRequest;
import ru.practicum.entity.Event;
import ru.practicum.entity.Compilation;
import ru.practicum.exception.NotFoundException;
import ru.practicum.storage.CompilationStorage;
import ru.practicum.storage.EventStorage;
import ru.practicum.utility.Mapper;
import ru.practicum.utility.Paginator;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class CompilationService {
    private final CompilationStorage storage;
    private final EventStorage eventStorage;


    @Transactional(readOnly = true)
    public List<CompilationsDto> getCompilations(Boolean pinned, int from, int size) {
        List<CompilationsDto> foundCompilations;
        PageRequest page = Paginator.simplePage(from, size);
        if (pinned == null) {
            foundCompilations = storage.findAll(page)
                    .stream().map(Mapper::toCompDto).collect(Collectors.toList());
        } else {
            foundCompilations = storage.findAllByPinned(pinned, page)
                    .stream().map(Mapper::toCompDto).collect(Collectors.toList());
        }
        log.info("Найден список из {} подборок", foundCompilations.size());
        return foundCompilations;
    }

    @Transactional(readOnly = true)
    public CompilationsDto getCompilationById(long compId) {
        CompilationsDto foundComp = Mapper.toCompDto(checkCompilation(compId));
        log.info("Найдена подборка с id {}", compId);
        return foundComp;
    }

    @Transactional
    public CompilationsDto createCompilation(NewCompilationDto creatingDto) {
        List<Event> eventList = eventStorage.findAllById(creatingDto.getEvents());
        Compilation savedComp = storage.save(Mapper.toCompilation(creatingDto, eventList));
        log.info("Новая подборка сохранена с id {}", savedComp.getId());
        return Mapper.toCompDto(savedComp);
    }

    @Transactional
    public void deleteCompilation(long compId) {
        checkCompilation(compId);
        storage.deleteById(compId);
        log.info("Подборка с id {} удалена", compId);
    }

    @Transactional
    public CompilationsDto updateCompilation(long compId, UpdateCompilationRequest updatingDto) {
        Compilation updatingComp = checkCompilation(compId);

        if (updatingDto.getPinned() != null) updatingComp.setPinned(updatingDto.getPinned());
        if (updatingDto.getTitle() != null) updatingComp.setTitle(updatingDto.getTitle());
        if (updatingDto.getEvents() != null) {
            List<Event> eventList = eventStorage.findAllByIdIn(updatingDto.getEvents());
            updatingComp.setEvents(eventList);
        }

        Compilation updatedComp = storage.save(updatingComp);
        log.info("Подборка с id {} обновлена: {}", compId, updatedComp.getEvents());

        return Mapper.toCompDto(updatedComp);
    }

    private Compilation checkCompilation(long compId) {
        return storage.findById(compId).orElseThrow(() ->
                new NotFoundException(String.format("Подборка с id %d не существует", compId)));
    }
}
