package ru.practicum.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class UserDto {
    private String email;
    private Long id;
    private String name;
}
