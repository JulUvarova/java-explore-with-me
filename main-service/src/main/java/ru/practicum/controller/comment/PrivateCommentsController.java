package ru.practicum.controller.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.comment.CommentDtoResponse;
import ru.practicum.dto.comment.NewCommentDto;
import ru.practicum.service.CommentService;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/comments")
public class PrivateCommentsController {
    private final CommentService service;

    /**
     * Добавление нового комментария к событию
     * Можно добавить только к опубликованному событию
     */
    @PostMapping("/events/{eventId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDtoResponse createComment(@PathVariable long userId,
                                            @PathVariable long eventId,
                                            @Valid @RequestBody NewCommentDto creatingDto) {
        log.info("Добавление нового комментария {} от пользователя {} к событию {}", creatingDto, userId, eventId);
        return service.createComment(userId, eventId, creatingDto);
    }

    /**
     * Изменение комментария
     * Не позднее, чем через 1 час после публикации
     * Доступно только автору комментария
     */
    @PatchMapping("/{comId}")
    public CommentDtoResponse updateCommentById(@PathVariable long userId,
                                        @PathVariable long comId,
                                        @RequestBody @Valid NewCommentDto updatingDto) {
        log.info("Изменение комментария {} от пользователя {}: {}", comId, userId, updatingDto);
        return service.updateCommentById(userId, comId, updatingDto);
    }

    /**
     * Удаление комментария его автором
     * Не позднее, чем через 1 час после публикации
     */
    @DeleteMapping("/{comId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable long userId, @PathVariable long comId) {
        log.info("Удаление комментария с id: {}", comId);
        service.deleteComment(userId, comId);
    }
}
