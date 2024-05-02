package ru.practicum.dto.compilation;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@SuperBuilder
public class UpdateCompilationRequest {
    Set<Long> events = null;

    Boolean pinned = null;

    @Size(min = 1, max = 50)
    String title;
}
