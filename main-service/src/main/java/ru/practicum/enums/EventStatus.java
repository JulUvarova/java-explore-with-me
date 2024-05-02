package ru.practicum.enums;

import ru.practicum.exception.ValidationException;

public enum EventStatus {
    PENDING,
    PUBLISHED,
    CANCELED;

    public static EventStatus toState(String state) {
        switch (state) {
            case "PENDING":
                return EventStatus.PENDING;
            case "PUBLISHED":
                return EventStatus.PUBLISHED;
            case "CANCELED":
                return EventStatus.CANCELED;
            default:
                throw new ValidationException(String.format("Unknown state: %s", state));
        }
    }
}
