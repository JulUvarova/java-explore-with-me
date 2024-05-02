package ru.practicum.controller.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.event.*;
import ru.practicum.dto.request.ParticipationRequestDto;
import ru.practicum.service.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/events")
public class PrivateEventsController {
    private final EventService service;

    /**
     * Получение событий, добавленных текущим пользователем
     * В случае, если по заданным фильтрам не найдено ни одного события, возвращает пустой список
     */
    @GetMapping
    public List<EventShortDto> getEventsByUser(@PathVariable long userId,
                                               @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                               @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Получение списка событий от пользователя с id {}", userId);
        return service.getEventsByUser(userId, from, size);
    }

    /**
     * Получение информации о событии, добавленном текущим пользователем
     * В случае, если события с заданным id не найдено, возвращает статус код 404
     */
    @GetMapping("/{eventId}")
    public EventFullDto getEventByIdAndUser(@PathVariable long userId, @PathVariable long eventId) {
        log.info("Получение события с id {} от пользователя с id {}", eventId, userId);
        return service.getEventByIdAndUser(userId, eventId);
    }

    /**
     * Получение информации о запросах неа участие в событии текущего пользователя
     * В случае, если по заданным фильтрам не найдено ни одной заявки, возвращает пустой список
     */
    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getRequestsByUser(@PathVariable long userId, @PathVariable long eventId) {
        log.info("Получение запросов на участии в событии с id {} от пользователя с id {}", eventId, userId);
        return service.getRequestsByUser(userId, eventId);
    }

    /**
     * Добавление нового события
     * Дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto createEvent(@PathVariable long userId, @Valid @RequestBody NewEventDto creatingDto) {
        log.info("Добавление нового события {} от пользователя {}", creatingDto, userId);
        return service.createEvent(userId, creatingDto);
    }

    /**
     * Изменение события, добавленного текущим пользователем
     */
    @PatchMapping("/{eventId}")
    public EventFullDto updateEventById(@PathVariable long userId,
                                        @PathVariable long eventId,
                                        @RequestBody @Valid UpdateEventUserRequest updatingDto) {
        log.info("Изменение события с id {} от пользователя с id {} запроса: {}", eventId, userId, updatingDto);
        return service.updateEventById(userId, eventId, updatingDto);
    }

    /**
     * Изменение статуса (подтверждена/отменена) заявок на участие в событии текущего пользователя
     */
    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult updateEventStatus(@PathVariable long userId,
                                                            @PathVariable long eventId,
                                                            @RequestBody(required = false) EventRequestStatusUpdateRequest updatingDto) {
        log.info("Изменение статуса запроса с id {} от пользователя с id {}: {}", eventId, userId, updatingDto);
        return service.updateEventStatus(userId, eventId, updatingDto);
    }
}
