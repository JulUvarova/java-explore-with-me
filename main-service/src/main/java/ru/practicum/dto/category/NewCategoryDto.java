package ru.practicum.dto.category;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@SuperBuilder
public class NewCategoryDto {
    @Size(max = 50, message = "Превышен максимальный размер в 50 символов")
    @NotBlank(message = "Имя категории не может быть пустым")
    private String name;
}
