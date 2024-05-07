package ru.practicum.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.practicum.enums.RequestStatus;
import ru.practicum.utility.Constant;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@SuperBuilder
public class ParticipationRequestDto {
    @JsonFormat(pattern = Constant.DATA_FORMAT)
    private LocalDateTime created;
    private Long event;
    private Long id;
    private Long requester;
    private RequestStatus status;
}
