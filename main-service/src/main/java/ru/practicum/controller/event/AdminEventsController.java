package ru.practicum.controller.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.event.EventFullDto;
import ru.practicum.dto.event.UpdateEventAdminRequest;
import ru.practicum.exception.ValidationException;
import ru.practicum.service.EventService;
import ru.practicum.utility.Constant;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/admin/events")
public class AdminEventsController {
    private final EventService service;

    /**
     * Поиск событий
     * Эндпоинт возвращает полную информацию обо всех событиях подходящих под переданные условия
     * В случае, если по заданным фильтрам не найдено ни одного события, возвращает пустой список
     */
    @GetMapping
    public List<EventFullDto> getEventsByParam(
            @RequestParam(required = false) List<Long> users,
            @RequestParam(required = false) List<String> states,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) @DateTimeFormat(pattern = Constant.DATA_FORMAT) LocalDateTime rangeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = Constant.DATA_FORMAT) LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(defaultValue = "10") @Positive int size) {

        if (rangeEnd != null && rangeStart != null && rangeEnd.isBefore(rangeStart))
            throw new ValidationException("Окончание события должны быть позже его начала");

        log.info("Получение списка событий от пользователей: {}, статусом: {}, категорий: {}, начало: {}, конец: {}",
                users, states, categories, rangeStart, rangeEnd);
        return service.getEventsByParam(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    /**
     * Редактирование данных события и его статуса (отклонение/публикация).
     * Редактирование данных любого события администратором.
     * Валидация данных не требуется.
     */
    @PatchMapping("/{eventId}")
    public EventFullDto updateEvent(@PathVariable long eventId,
                                    @RequestBody @Valid UpdateEventAdminRequest updatingDto) {
        log.info("Обновление события с id {}: {}", eventId, updatingDto);
        return service.updateEvent(eventId, updatingDto);
    }
}
