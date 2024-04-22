package ru.practicum.stats.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.stats.dto.annotation.CheckIpAddress;

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

    @CheckIpAddress
    private String ip;

    @NotNull(message = "Дата и время не может быть пустым")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
}
