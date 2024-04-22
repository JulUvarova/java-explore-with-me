package ru.practicum.stats.service;

import ru.practicum.stats.dto.ViewStatsDto;
import ru.practicum.stats.entity.EndpointHit;
import ru.practicum.stats.mapper.StatsMapper;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.stats.storage.StatsStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.stats.dto.EndpointHitDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class StatsService {
    private final StatsStorage storage;

    @Autowired
    public StatsService(StatsStorage storage) {
        this.storage = storage;
    }

    @Transactional
    public void addHit(EndpointHitDto endpointHitRequest) {
        EndpointHit savedEntity = storage.save(StatsMapper.toEndpointHitEntity(endpointHitRequest));
        log.info("Просмотр сохранен в базе с id {}", savedEntity.getId());
    }

    @Transactional(readOnly = true)
    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uriList, Boolean isUnique) {
        List<ViewStatsDto> endpointHits = new ArrayList<>();
        if (isUnique && uriList == null) {
            endpointHits = storage.getUniqueByTimes(start, end);
        } else if (isUnique && uriList != null) {
            endpointHits = storage.getUniqueByTimesAndList(uriList, start, end);
        } else if (!isUnique && uriList == null) {
            endpointHits = storage.getAllByTime(start, end);
        } else if (!isUnique && uriList != null) {
            endpointHits = storage.getAllByTimeAndList(uriList, start, end);
        }
        log.info("Возвращен список из {} объектов", endpointHits.size());
        return endpointHits;
    }
}
