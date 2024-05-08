package ru.practicum.dto.comment;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.practicum.dto.user.UserShortDto;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@SuperBuilder
public class CommentDtoResponse {
    private Long id;
    private String text;
    private Long eventId;
    private UserShortDto author;
    private LocalDateTime created;
}
