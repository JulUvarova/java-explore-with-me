package ru.practicum.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.entity.Request;

import java.util.List;

public interface RequestStorage extends JpaRepository<Request, Long> {
    List<Request> findAllByRequesterId(long userId);

    List<Request> findAllByEventId(long eventId);

    List<Request> findAllByRequesterIdAndEventId(long userId, long eventId);
}
