package ru.practicum.controller.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.compilation.CompilationsDto;
import ru.practicum.service.CompilationService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/compilations")
public class PublicCompilationsController {
    private final CompilationService service;

    /**
     * Получение подборок событий
     * В случае, если по заданным фильтрам не найдено ни одной подборки, возвращает пустой список
     */
    @GetMapping
    List<CompilationsDto> getCompilations(@RequestParam(required = false) Boolean pinned,
                                          @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                          @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Получение подборки событий: закрепленные - {}, количество пропущенных - {}, элементов в наборе - {}",
                pinned, from, size);
        return service.getCompilations(pinned, from, size);
    }

    /**
     * Получение подборок событий по его id
     * В случае, если подборки с заданным id не найдено, возвращает статус код 404
     */
    @GetMapping("/{compId}")
    CompilationsDto getCompilationById(@PathVariable long compId) {
        log.info("Получение подборки событий по id = {}", compId);
        return service.getCompilationById(compId);
    }
}