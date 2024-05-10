package ru.practicum.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.dto.comment.NewCommentDto;
import ru.practicum.entity.Comment;
import ru.practicum.entity.User;
import ru.practicum.exception.ConflictException;
import ru.practicum.storage.CommentStorage;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest { // то, что отсутствует в Postman
    @InjectMocks
    private CommentService service;

    @Mock
    private CommentStorage commentStorage;

    private Comment testComment;

    @BeforeEach
    void init() {
        testComment = Comment.builder()
                .id(1L)
                .text("Test me")
                .created(LocalDateTime.now().minusHours(1))
                .eventId(1L)
                .author(User.builder()
                        .id(1L)
                        .name("Ivan Ivanich")
                        .email("imgroot@mail.ru")
                        .build())
                .build();
    }

    @Test
    void updateCommentById_whenTimeIsOver_thenReturnError() {
        when(commentStorage.findById(anyLong()))
                .thenReturn(Optional.of(testComment));

        ConflictException exception = assertThrows(ConflictException.class,
                () -> service.updateCommentById(1L, 1L, any(NewCommentDto.class)));

        assertEquals(String.format("Время для изменения комментария вышло"), exception.getMessage());
        verify(commentStorage, times(0)).save(testComment);
    }

    @Test
    void deleteComment_whenTimeIsOver_thenReturnError() {
        when(commentStorage.findById(anyLong()))
                .thenReturn(Optional.of(testComment));

        ConflictException exception = assertThrows(ConflictException.class,
                () -> service.deleteComment(1L, 1L));

        assertEquals(String.format("Время для изменения комментария вышло"), exception.getMessage());
        verify(commentStorage, times(0)).deleteById(testComment.getId());
    }
}