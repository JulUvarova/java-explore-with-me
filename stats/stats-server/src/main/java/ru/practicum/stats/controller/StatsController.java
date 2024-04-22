package ru.practicum.stats.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.dto.ViewStatsDto;
import ru.practicum.stats.exception.InvalidArgumentsException;
import ru.practicum.stats.service.StatsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Validated
@RestController
public class StatsController {
    private final StatsService service;

    @Autowired
    public StatsController(StatsService service) {
        this.service = service;
    }

    /**
     * Сохранение информации о том, что на uri конкретного сервиса был отправлен запрос пользователем.
     * Название сервиса, uri и ip пользователя указаны в теле запроса.
     */
    @PostMapping("/hit")
    public void addHit(@Validated @RequestBody EndpointHitDto endpointHitRequest) {
        service.addHit(endpointHitRequest);
        log.info("Получен запрос на сохранение информации: {}", endpointHitRequest);
    }

    /**
     * Получение статистики по посещениям
     */
    @GetMapping("/stats")
    public List<ViewStatsDto> getStats(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
            @RequestParam(name = "uris", required = false) List<String> uriList,
            @RequestParam(name = "unique", defaultValue = "false") Boolean isUnique) {

        checkData(start, end);

        log.info("Получен запрос на статистику посещений в период с {} до {} по сервисам {} и уникальности {}",
                start, end, uriList, isUnique);
        return service.getStats(start, end, uriList, isUnique);
    }

    private void checkData(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null || end.isBefore(start)) {
            throw new InvalidArgumentsException("Неверно указан временной диапазон");
        }
    }
}
