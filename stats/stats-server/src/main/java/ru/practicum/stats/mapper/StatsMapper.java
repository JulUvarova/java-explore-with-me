package ru.practicum.stats.mapper;

import ru.practicum.stats.entity.EndpointHit;
import lombok.experimental.UtilityClass;
import ru.practicum.stats.dto.EndpointHitDto;

@UtilityClass
public class StatsMapper {
    public EndpointHit toEndpointHitEntity(EndpointHitDto endpointHitRequest) {
        return EndpointHit.builder()
                .app(endpointHitRequest.getApp())
                .ip(endpointHitRequest.getIp())
                .uri(endpointHitRequest.getUri())
                .times(endpointHitRequest.getTimestamp())
                .build();
    }
}
