package ru.practicum.stats.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EndpointHitDto {
    @NotBlank(message = "Идентификатор не может быть пустым")
    @Size(max = 255, message = "Превышено число символов")
    private String app;

    @NotBlank(message = "URI не может быть пустым")
    @Size(max = 255, message = "Превышено число символов")
    private String uri;

    @NotBlank(message = "IP не может быть пустым")
    @Size(max = 255, message = "Превышено число символов")
    private String ip;

    @NotNull(message = "Дата и время не может быть пустым")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
}
