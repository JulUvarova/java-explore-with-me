package ru.practicum.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.request.ParticipationRequestDto;
import ru.practicum.entity.Event;
import ru.practicum.entity.Request;
import ru.practicum.entity.User;
import ru.practicum.enums.EventStatus;
import ru.practicum.enums.RequestStatus;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.storage.EventStorage;
import ru.practicum.storage.RequestStorage;
import ru.practicum.storage.UserStorage;
import ru.practicum.utility.Mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class RequestService {
    private final UserStorage userStorage;
    private final RequestStorage requestStorage;
    private final EventStorage eventStorage;

    @Transactional(readOnly = true)
    public List<ParticipationRequestDto> getRequestsByUser(long userId) {
        checkUser(userId);
        List<Request> requestList = requestStorage.findAllByRequesterId(userId);
        log.info("Найден список из {} запросов", requestList.size());
        return requestList.stream().map(Mapper::toRequestDto).collect(Collectors.toList());
    }

    @Transactional
    public ParticipationRequestDto createRequest(long userId, long eventId) {
        if (!requestStorage.findAllByRequesterIdAndEventId(userId, eventId).isEmpty())
            throw new ConflictException("Запрос уже существует");

        User user = checkUser(userId);
        Event event = checkEvent(eventId);

        if (userId == event.getInitiator().getId()
                || !event.getState().equals(EventStatus.PUBLISHED))
            throw new ConflictException(String.format(
                    "Нельзя создать запрос на участие в событии %d от пользователя %d", eventId, userId));

        log.info("Лимит участников: {}, уже записано: {}, премодерация {}",
                event.getParticipantLimit(), event.getConfirmedRequests(), event.getRequestModeration());

        if (event.getParticipantLimit() != 0 && event.getConfirmedRequests() >= event.getParticipantLimit())
            throw new ConflictException("Нет свободных мест");

        Request request = new Request();

        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            request.setStatus(RequestStatus.CONFIRMED);
        } else {
            request.setStatus(RequestStatus.PENDING);
        }

        request.setCreated(LocalDateTime.now());
        request.setRequester(user);
        request.setEvent(event);

        Request savedRequest = requestStorage.save(request);

        log.info("Заявка создана {}", savedRequest);
        return Mapper.toRequestDto(savedRequest);
    }

    @Transactional
    public ParticipationRequestDto cancelRequest(long userId, long requestId) {
        checkUser(userId);
        Request request = checkRequest(requestId);
        request.setStatus(RequestStatus.CANCELED);
        Request updatedRequest = requestStorage.save(request);
        log.info("Заявка с id {} отменена", requestId);
        return Mapper.toRequestDto(updatedRequest);
    }

    private User checkUser(long userId) {
        return userStorage.findById(userId).orElseThrow(() -> new NotFoundException(String.format(
                "Пользователь с id %d не существует", userId)));
    }

    private Event checkEvent(long eventId) {
        return eventStorage.findById(eventId).orElseThrow(() -> new NotFoundException(String.format(
                "Событие с id %d не существует", eventId)));
    }

    private Request checkRequest(long requestId) {
        return requestStorage.findById(requestId).orElseThrow(() -> new NotFoundException(String.format(
                "Запрос с id %d не существует", requestId)));
    }
}
