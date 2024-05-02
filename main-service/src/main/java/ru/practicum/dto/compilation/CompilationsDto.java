package ru.practicum.dto.compilation;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.practicum.dto.event.EventShortDto;

import java.util.List;

@Data
@NoArgsConstructor
@SuperBuilder
public class CompilationsDto {
    List<EventShortDto> events;
    Long id;
    Boolean pinned;
    String title;
}
