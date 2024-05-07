package ru.practicum.controller.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.request.ParticipationRequestDto;
import ru.practicum.service.RequestService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/requests")
public class PrivateRequestsController {
    private final RequestService service;

    /**
     * Получение информации о заявках текущего пользователя на участие в чужих событиях
     * В случае, если по заданным фильтрам не найдено ни одной заявки, возвращает пустой список
     */
    @GetMapping
    public List<ParticipationRequestDto> getRequestsByUser(@PathVariable long userId) {
        log.info("Получение списка запросов пользователя с id {}", userId);
        return service.getRequestsByUser(userId);
    }

    /**
     * Добавление запроса от текущего пользователя на участие в событии
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto createRequest(@PathVariable long userId,
                                     @RequestParam long eventId) {
        log.info("Запрос от пользователя с id {} на участие в событии с id {}", userId, eventId);
        return service.createRequest(userId, eventId);
    }

    /**
     * Отмена своего запроса на участие в событии
     */
    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable long userId,
                                      @PathVariable long requestId) {
        log.info("Отмена пользователем с id {} запроса с id {}", userId, requestId);
        return service.cancelRequest(userId, requestId);
    }
}
