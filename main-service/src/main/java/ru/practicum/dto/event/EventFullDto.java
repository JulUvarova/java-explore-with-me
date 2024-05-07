package ru.practicum.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.practicum.dto.location.LocationDto;
import ru.practicum.dto.user.UserShortDto;
import ru.practicum.enums.EventStatus;
import ru.practicum.dto.category.CategoryDto;
import ru.practicum.utility.Constant;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@SuperBuilder
public class EventFullDto {
    private String annotation;
    private CategoryDto category;
    private Integer confirmedRequests;

    @JsonFormat(pattern = Constant.DATA_FORMAT)
    private LocalDateTime createdOn;

    private String description;

    @JsonFormat(pattern = Constant.DATA_FORMAT)
    private LocalDateTime eventDate;

    private Long id;
    private UserShortDto initiator;
    private LocationDto location;
    private boolean paid;
    private Integer participantLimit;

    @JsonFormat(pattern = Constant.DATA_FORMAT)
    private LocalDateTime publishedOn;

    private Boolean requestModeration;
    private EventStatus state;
    private String title;
    private Integer views;
}
