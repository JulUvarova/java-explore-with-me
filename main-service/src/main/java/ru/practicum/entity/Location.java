package ru.practicum.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@SuperBuilder
@NoArgsConstructor
@Embeddable
public class Location {
    private Float lat;
    private Float lon;
}
