package ru.practicum.dto.category;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class CategoryDto {
    private Long id;
    private String name;
}
