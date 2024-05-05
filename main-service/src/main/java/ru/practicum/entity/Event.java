package ru.practicum.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import ru.practicum.enums.EventStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Data
@SuperBuilder
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "annotation")
    private String annotation;

    @JoinColumn(name = "category_id")
    @ManyToOne
    @ToString.Exclude
    private Category category;

    @CreationTimestamp
    @Column(name = "created_on")
    private LocalDateTime created;

    @Column(name = "description")
    private String description;

    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @JoinColumn(name = "initiator_id")
    @ManyToOne
    @ToString.Exclude
    private User initiator;

    @Column(name = "paid")
    private Boolean paid;

    @Column(name = "participant_limit")
    private Integer participantLimit;

    @Column(name = "published_on")
    private LocalDateTime published;

    @Column(name = "request_moderation")
    private Boolean requestModeration;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private EventStatus state;

    @Column(name = "title")
    private String title;

    @Embedded
    @AttributeOverride(name = "lat", column = @Column(name = "location_lat"))
    @AttributeOverride(name = "lon", column = @Column(name = "location_lon"))
    private Location location;

    @Formula("(select count(r.id) " +
            "from events as e " +
            "left join requests as r ON e.id = r.event_id and r.status = 'CONFIRMED'  " +
            "where id = r.event_id)")
    private int confirmedRequests;
}