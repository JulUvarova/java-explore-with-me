package ru.practicum.dto.event;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.practicum.dto.request.ParticipationRequestDto;

import java.util.List;

@Data
@NoArgsConstructor
@SuperBuilder
public class EventRequestStatusUpdateResult {
    private List<ParticipationRequestDto> confirmedRequests;
    private List<ParticipationRequestDto> rejectedRequests;
}
