package ru.practicum.dto.event;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.practicum.enums.RequestStatus;

import java.util.List;

@Data
@NoArgsConstructor
@SuperBuilder
public class EventRequestStatusUpdateRequest {
    private List<Long> requestIds;
    private RequestStatus status;
}
