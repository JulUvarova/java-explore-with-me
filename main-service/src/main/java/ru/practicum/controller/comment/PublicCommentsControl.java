package ru.practicum.controller.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.comment.CommentDtoResponse;
import ru.practicum.service.CommentService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/comments")
public class PublicCommentsControl {
    private final CommentService service;


    /**
     * Получение информации о комментарии по его id
     */
    @GetMapping("/{id}")
    public CommentDtoResponse getCommentById(@PathVariable long id) {
        log.info("Получение комментария с id {}", id);
        return service.getCommentById(id);
    }


    /**
     * Получение комментариев к событию
     */
    @GetMapping("/events/{eventId}")
    public List<CommentDtoResponse> getCommentsByEventId(
            @PathVariable long eventId,
            @RequestParam(name = "sort", defaultValue = "true") Boolean isAsc,
            @RequestParam(defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(defaultValue = "10") @Positive int size) {

        log.info("Получение списка комментариев по событию {}, группировка от более свежих: {}", eventId, isAsc);
        return service.getCommentsByEventId(eventId, isAsc, from, size);
    }
}
