package ru.practicum.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.practicum.enums.RequestStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
@Data
@SuperBuilder
@NoArgsConstructor
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "requester_id")
    @ManyToOne
    private User requester;

    @Column(name = "created")
    private LocalDateTime created;

    @JoinColumn(name = "event_id")
    @ManyToOne
    private Event event;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
}
