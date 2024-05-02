package ru.practicum.controller.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.compilation.CompilationsDto;
import ru.practicum.dto.compilation.NewCompilationDto;
import ru.practicum.dto.compilation.UpdateCompilationRequest;
import ru.practicum.service.CompilationService;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/admin/compilations")
public class AdminCompilationsController {
    private final CompilationService service;

    /**
     * Добавление новой подборки
     * Подборка может не содержать событий
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationsDto createCompilation(@RequestBody @Valid NewCompilationDto creatingDto) {
        log.info("Создание новой подборки событий: {}", creatingDto);
        return service.createCompilation(creatingDto);
    }

    /**
     * Удаление подборки
     */
    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable long compId) {
        log.info("Удаление подборки с id {}", compId);
        service.deleteCompilation(compId);
    }

    /**
     * Изменение подборки
     */
    @PatchMapping("/{compId}")
    public CompilationsDto updateCompilation(@PathVariable long compId,
                                          @RequestBody @Valid UpdateCompilationRequest updatingDto) {
        log.info("Обновление категории с id {}", compId);
        return service.updateCompilation(compId, updatingDto);
    }
}
