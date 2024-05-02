package ru.practicum.storage;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.practicum.enums.EventStatus;
import ru.practicum.entity.Event;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EventStorage extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
    List<Event> findAllByIdIn(Set<Long> events);

    List<Event> findEventByInitiatorId(long userId, PageRequest pageRequest);

    Optional<Event> findEventByIdAndInitiatorId(long eventId, long userId);

    Optional<Event> findByIdAndState(long id, EventStatus state);
}
