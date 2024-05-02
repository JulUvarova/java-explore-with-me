package ru.practicum.dto.compilation;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@SuperBuilder
public class NewCompilationDto {
    Set<Long> events = new HashSet<>(); // может не содержать событий

    Boolean pinned = false;

    @NotBlank
    @Size(min = 1, max = 50)
    String title;
}
