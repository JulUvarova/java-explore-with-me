package ru.practicum.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@SuperBuilder
public class NewUserRequest {
    @NotBlank
    @Size(min = 6, max = 254)
    @Email(message = "Почта должна быть оформлена по правилам")
    private String email;

    @NotBlank
    @Size(min = 2, max = 250)
    private String name;
}
