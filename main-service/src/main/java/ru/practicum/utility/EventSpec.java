package ru.practicum.utility;

import lombok.ToString;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import ru.practicum.entity.Event;
import ru.practicum.enums.EventStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@UtilityClass
@ToString
public class EventSpec {
    public Specification<Event> initiatorsIn(List<Long> users) {
        if (Objects.isNull(users) || users.isEmpty()) return null;
        return ((root, query, criteriaBuilder) -> root.get("initiator").get("id").in(users));
    }

    public Specification<Event> stateIn(List<String> states) {
        if (Objects.isNull(states) || states.isEmpty()) return null;
        List<EventStatus> eventStatuses = states.stream()
                .map(EventStatus::toState).collect(Collectors.toList());
        return ((root, query, criteriaBuilder) -> root.get("state").in(eventStatuses));
    }

    public Specification<Event> categoriesIn(List<Long> categories) {
        if (Objects.isNull(categories) || categories.isEmpty()) return null;
        return ((root, query, criteriaBuilder) -> root.get("category").get("id").in(categories));
    }

    public Specification<Event> dateTimeAfter(LocalDateTime rangeStart) {
        if (Objects.isNull(rangeStart)) return null;
        return ((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("eventDate"), rangeStart));
    }

    public Specification<Event> dateTimeBefore(LocalDateTime rangeEnd) {
        if (Objects.isNull(rangeEnd)) return null;
        return ((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("eventDate"), rangeEnd));
    }

    public Specification<Event> isPaid(Boolean paid) {
        if (Objects.isNull(paid)) return null;
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("paid"), paid));
    }

    public Specification<Event> containText(String text) {
        if (Objects.isNull(text)) return null;
        return ((root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.like(criteriaBuilder.lower(root.get("annotation")),
                        "%" + text.toLowerCase() + "%"),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),
                        "%" + text.toLowerCase() + "%")));
    }

    public static Specification<Event> dateTimeAfterOrNow(LocalDateTime rangeStart) {
        if (Objects.isNull(rangeStart))
            return ((root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("eventDate"),
                    LocalDateTime.now()));
        return ((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("eventDate"), rangeStart));
    }

    public static Specification<Event> dateTimeBeforeOrNow(LocalDateTime rangeEnd) {
        if (Objects.isNull(rangeEnd))
            return ((root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("eventDate"),
                    LocalDateTime.now()));
        return ((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("eventDate"), rangeEnd));
    }
}