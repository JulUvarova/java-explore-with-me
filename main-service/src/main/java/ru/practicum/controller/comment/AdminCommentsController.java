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
@RequestMapping(path = "/admin/comments")
public class AdminCommentsController {
    private final CommentService service;

    /**
     * Модерация комментария администратором
     */
    @PatchMapping("/{comId}")
    public CommentDtoResponse moderateCommentById(@PathVariable long comId,
                                                @RequestBody @Valid NewCommentDto updatingDto) {
        log.info("Модерация комментария {}: {}", comId, updatingDto);
        return service.moderateCommentById(comId, updatingDto);
    }

    /**
     * Удаление комментария администраторов
     */
    @DeleteMapping("/{comId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void admDeleteComment(@PathVariable long comId) {
        log.info("Удаление комментария с id: {}", comId);
        service.admDeleteComment(comId);
    }
}
