package ru.practicum.controller.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.event.EventFullDto;
import ru.practicum.dto.event.EventShortDto;
import ru.practicum.exception.ValidationException;
import ru.practicum.service.EventService;
import ru.practicum.utility.Constant;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/events")
public class PublicEventsController {
    private final EventService service;

    /**
     * Получение событий с возможностью фильтрации
     * В случае, если по заданным фильтрам не найдено ни одного события, возвращает пустой список
     */
    @GetMapping
    public List<EventShortDto> getPublicEventsByParam(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) @DateTimeFormat(pattern = Constant.DATA_FORMAT) LocalDateTime rangeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = Constant.DATA_FORMAT) LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(defaultValue = "10") @Positive int size,
            HttpServletRequest request) {

        if (rangeEnd != null && rangeStart != null && rangeEnd.isBefore(rangeStart))
            throw new ValidationException("Окончание события должны быть позже его начала");

        log.info("Получение списка событий по тексту: {}, категориям: {}, оплата: {}, начало: {}, конец: {}, лимит: {}",
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable);
        return service.getPublicEventsByParam(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, from, size, request);
    }

    /**
     * Получение информации об опубликованном событии по его id
     * В случае, если события с заданным id не найдено, возвращает статус код 404
     */
    @GetMapping("/{id}")
    public EventFullDto getEventById(@PathVariable long id, HttpServletRequest request) {
        log.info("Получение события с id {}", id);
        return service.getEventById(id, request);
    }
}
