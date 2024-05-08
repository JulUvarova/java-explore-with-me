package ru.practicum.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.dto.comment.CommentDtoResponse;
import ru.practicum.dto.comment.NewCommentDto;
import ru.practicum.entity.Comment;
import ru.practicum.entity.Event;
import ru.practicum.entity.User;
import ru.practicum.enums.EventStatus;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.storage.CommentStorage;
import ru.practicum.storage.EventStorage;
import ru.practicum.storage.UserStorage;
import ru.practicum.utility.Mapper;
import ru.practicum.utility.Paginator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class CommentService {
    private final UserStorage userStorage;
    private final EventStorage eventStorage;
    private final CommentStorage commentStorage;

    public CommentDtoResponse createComment(long userId, long eventId, NewCommentDto creatingDto) {
        User author = checkUser(userId);
        checkEvent(eventId);

        Comment comment = Mapper.toComment(creatingDto, eventId, author);
        Comment createdComment = commentStorage.save(comment);

        log.info("Создан новый комментарий: {}", createdComment);
        return Mapper.toCommentResponse(createdComment);
    }

    public CommentDtoResponse updateCommentById(long userId, long comId, NewCommentDto updatingDto) {
        Comment oldComment = checkComment(comId);

        if (userId != oldComment.getAuthor().getId()) {
            throw new ConflictException(String.format("Пользователь {} не является автором комментария", userId));
        }
        if (LocalDateTime.now().isAfter(oldComment.getCreated().plusHours(1))) {
            throw new ConflictException("Время для изменения комментария вышло");
        }
        if (oldComment.getText().equals(updatingDto.getText())) {
            log.info("Комментарий не содержит изменений");
            return Mapper.toCommentResponse(oldComment);
        }

        oldComment.setText(updatingDto.getText());
        Comment newComment = commentStorage.save(oldComment);

        log.info("Комментарий изменен: {}", newComment);
        return Mapper.toCommentResponse(newComment);
    }

    public CommentDtoResponse moderateCommentById(long comId, NewCommentDto updatingDto) {
        Comment oldComment = checkComment(comId);

        if (oldComment.getText().equals(updatingDto.getText())) {
            log.info("Комментарий не содержит изменений");
            return Mapper.toCommentResponse(oldComment);
        }

        oldComment.setText(updatingDto.getText());
        Comment newComment = commentStorage.save(oldComment);

        log.info("Комментарий изменен: {}", newComment);
        return Mapper.toCommentResponse(newComment);
    }

    public void deleteComment(long userId, long comId) {
        Comment comment = checkComment(comId);

        if (userId != comment.getAuthor().getId()) {
            throw new ConflictException(String.format("Пользователь {} не является автором комментария", userId));
        }
        if (LocalDateTime.now().isAfter(comment.getCreated().plusHours(1))) {
            throw new ConflictException("Время для изменения комментария вышло");
        }

        commentStorage.deleteById(comId);
        log.info("Комментарий с id {} удален", comId);
    }

    public void admDeleteComment(long comId) {
        checkComment(comId);

        commentStorage.deleteById(comId);
        log.info("Комментарий с id {} удален", comId);
    }

    public CommentDtoResponse getCommentById(long id) {
        Comment comment = checkComment(id);

        log.info("Найден комментарий: {}", comment);
        return Mapper.toCommentResponse(comment);
    }

    public List<CommentDtoResponse> getCommentsByEventId(long eventId, Boolean isAsc, int from, int size) {
        checkEvent(eventId);
        PageRequest page;
        if (isAsc) {
            page = Paginator.withSort(from, size, Sort.by(Sort.Direction.ASC, "created"));
        } else {
            page = Paginator.withSort(from, size, Sort.by(Sort.Direction.DESC, "created"));
        }

        List<Comment> comments = commentStorage.getAllByEventId(eventId, page);

        log.info("Найдено {} комментариев к событию", comments.size());
        return comments.stream()
                .map(Mapper::toCommentResponse)
                .collect(Collectors.toList());
    }

    private User checkUser(long userId) {
        return userStorage.findById(userId).orElseThrow(() -> new NotFoundException(String.format(
                "Пользователь с id %d не существует", userId)));
    }

    private Comment checkComment(long comId) {
        return commentStorage.findById(comId).orElseThrow(() -> new NotFoundException(String.format(
                "Комментария с id %d не существует", comId)));
    }

    private Event checkEvent(long eventId) {
        return eventStorage.findByIdAndState(eventId, EventStatus.PUBLISHED).orElseThrow(()
                -> new NotFoundException(String.format("Событие с id %d не существует или не опубликовано", eventId)));
    }
}
