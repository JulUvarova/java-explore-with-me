package ru.practicum.exception;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionApiHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMethodArgumentNotValidException(Exception ex) {
        String message = ex.getMessage();
        log.info("Получен статус 400: {}, {}", ex.getMessage(), ex.getStackTrace());
        return new ApiError(message, ex.getCause(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleIllegalArgException(Exception ex) {
        String message = ex.getMessage();
        log.info("Получен статус 400: {}, {}", ex.getMessage(), ex.getStackTrace());
        return new ApiError(message, ex.getCause(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(Exception ex) {
        String message = ex.getMessage();
        log.info("Получен статус 400: {}, {}", ex.getMessage(), ex.getStackTrace());
        return new ApiError(message, ex.getCause(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(Exception ex) {
        String message = ex.getMessage();
        log.info("Получен статус 404: {}, {}", ex.getMessage(), ex.getStackTrace());
        return new ApiError(message, ex.getCause(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflictException(Exception ex) {
        String message = ex.getMessage();
        log.info("Получен статус 409: {}, {}", ex.getMessage(), ex.getStackTrace());
        return new ApiError(message, ex.getCause(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleIllegalStateException(Exception ex) {
        String message = ex.getMessage();
        log.info("Получен статус 409: {}, {}", ex.getMessage(), ex.getStackTrace());
        return new ApiError(message, ex.getCause(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConstraintViolationException(Exception ex) {
        String message = ex.getMessage();
        log.info("Получен статус 409: {}, {}", ex.getMessage(), ex.getStackTrace());
        return new ApiError(message, ex.getCause(), HttpStatus.CONFLICT);
    }
}
