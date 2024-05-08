package ru.practicum.dto.comment;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@SuperBuilder
public class NewCommentDto {
    @NotBlank
    @Size(min = 2, max = 512)
    private String text;
}
