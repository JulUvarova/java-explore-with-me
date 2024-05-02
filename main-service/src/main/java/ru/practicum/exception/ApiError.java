package ru.practicum.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApiError {
    private final LocalDateTime time = LocalDateTime.now();
    private final String error;
    private final Throwable throwable;
    private final HttpStatus httpStatus;
}
