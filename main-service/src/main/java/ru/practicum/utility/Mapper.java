package ru.practicum.utility;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.comment.CommentDtoResponse;
import ru.practicum.dto.comment.NewCommentDto;
import ru.practicum.dto.compilation.CompilationsDto;
import ru.practicum.dto.compilation.NewCompilationDto;
import ru.practicum.dto.event.EventShortDto;
import ru.practicum.entity.*;
import ru.practicum.dto.category.CategoryDto;
import ru.practicum.dto.category.NewCategoryDto;
import ru.practicum.dto.event.EventFullDto;
import ru.practicum.dto.event.EventRequestStatusUpdateResult;
import ru.practicum.dto.event.NewEventDto;
import ru.practicum.dto.location.LocationDto;
import ru.practicum.dto.request.ParticipationRequestDto;
import ru.practicum.dto.user.NewUserRequest;
import ru.practicum.dto.user.UserDto;
import ru.practicum.dto.user.UserShortDto;
import ru.practicum.enums.EventStatus;
import ru.practicum.enums.RequestStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class Mapper {
    public User toUser(NewUserRequest dto) {
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
    }

    public UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public Category toCategory(NewCategoryDto dto) {
        return Category.builder()
                .name(dto.getName())
                .build();
    }

    public CategoryDto toCatDto(Category cat) {
        return CategoryDto.builder()
                .id(cat.getId())
                .name(cat.getName())
                .build();
    }

    public Compilation toCompilation(NewCompilationDto dto, List<Event> events) {
        return Compilation.builder()
                .title(dto.getTitle())
                .events(events)
                .pinned(dto.getPinned())
                .build();
    }

    public CompilationsDto toCompDto(Compilation comp) {
        return CompilationsDto.builder()
                .id(comp.getId())
                .title(comp.getTitle())
                .events(comp.getEvents()
                        .stream()
                        .map(e -> Mapper.toEventShortDto(e, 0))
                        .collect(Collectors.toList()))
                .pinned(comp.getPinned())
                .build();
    }

    public ParticipationRequestDto toRequestDto(Request request) {
        return ParticipationRequestDto.builder()
                .id(request.getId())
                .created(request.getCreated())
                .event(request.getEvent().getId())
                .requester(request.getRequester().getId())
                .status(request.getStatus())
                .build();
    }

    public EventShortDto toEventShortDto(Event event, int view) {
        return EventShortDto.builder()
                .id(event.getId())
                .paid(event.getPaid())
                .annotation(event.getAnnotation())
                .title(event.getTitle())
                .eventDate(event.getEventDate())
                .category(Mapper.toCatDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .initiator(Mapper.toUserShortDto(event.getInitiator()))
                .views(view)
                .build();
    }

    public EventFullDto toEventFullDto(Event event, int view) {
        return EventFullDto.builder()
                .id(event.getId())
                .paid(event.getPaid())
                .annotation(event.getAnnotation())
                .description(event.getDescription())
                .title(event.getTitle())
                .eventDate(event.getEventDate())
                .category(Mapper.toCatDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .initiator(Mapper.toUserShortDto(event.getInitiator()))
                .state(event.getState())
                .requestModeration(event.getRequestModeration())
                .views(view)
                .location(LocationDto.builder()
                        .lon(event.getLocation().getLon())
                        .lat(event.getLocation().getLat())
                        .build())
                .participantLimit(event.getParticipantLimit())
                .createdOn(event.getCreated())
                .publishedOn(event.getPublished())
                .build();
    }

    public UserShortDto toUserShortDto(User user) {
        return UserShortDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }

    public Location toLocation(LocationDto location) {
        return Location.builder()
                .lon(location.getLon())
                .lat(location.getLat())
                .build();
    }

    public Event toEvent(NewEventDto dto, Category category, User user) {
        return Event.builder()
                .annotation(dto.getAnnotation())
                .category(category)
                .description(dto.getDescription())
                .eventDate(dto.getEventDate())
                .initiator(user)
                .location(Mapper.toLocation(dto.getLocation()))
                .paid(dto.getPaid())
                .participantLimit(dto.getParticipantLimit())
                .published(null)
                .requestModeration(dto.getRequestModeration())
                .state(EventStatus.PENDING)
                .title(dto.getTitle())
                .build();
    }

    public EventRequestStatusUpdateResult toRequestResult(List<Request> requests) {
        List<ParticipationRequestDto> confirmedRequests = new ArrayList<>();
        List<ParticipationRequestDto> rejectedRequests = new ArrayList<>();
        for (Request req : requests) {
            if (req.getStatus().equals(RequestStatus.CONFIRMED)) confirmedRequests.add(Mapper.toRequestDto(req));
            else rejectedRequests.add(Mapper.toRequestDto(req));
        }
        return EventRequestStatusUpdateResult.builder()
                .confirmedRequests(confirmedRequests)
                .rejectedRequests(rejectedRequests)
                .build();
    }

    public Comment toComment(NewCommentDto creatingDto, long eventId, User user) {
        return Comment.builder()
                .text(creatingDto.getText())
                .author(user)
                .eventId(eventId)
                .build();
    }

    public CommentDtoResponse toCommentResponse(Comment createdComment) {
        return CommentDtoResponse.builder()
                .id(createdComment.getId())
                .text(createdComment.getText())
                .eventId(createdComment.getEventId())
                .author(Mapper.toUserShortDto(createdComment.getAuthor()))
                .created(createdComment.getCreated())
                .build();
    }
}
