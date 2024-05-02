package ru.practicum.dto.location;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class LocationDto {
    private float lat;
    private float lon;
}
