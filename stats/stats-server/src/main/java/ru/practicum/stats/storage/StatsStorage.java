package ru.practicum.stats.storage;

import org.springframework.data.jpa.repository.Query;
import ru.practicum.stats.dto.ViewStatsDto;
import ru.practicum.stats.entity.EndpointHit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsStorage extends JpaRepository<EndpointHit, Long> {
    @Query("select new ru.practicum.stats.dto.ViewStatsDto(s.app, s.uri, count(distinct s.ip) as hits) " +
            " from EndpointHit as s " +
            " where s.times between ?1 and ?2 " +
            " group by s.app, s.uri " +
            " order by hits desc ")
    List<ViewStatsDto> getUniqueByTimes(LocalDateTime start, LocalDateTime end);

    @Query("select new ru.practicum.stats.dto.ViewStatsDto(s.app, s.uri, count(distinct s.ip) as hits) " +
            " from EndpointHit as s " +
            " where s.uri in (?1) " +
            " and s.times between ?2 and ?3 " +
            " group by s.app, s.uri " +
            " order by hits desc ")
    List<ViewStatsDto> getUniqueByTimesAndList(List<String> uriList, LocalDateTime start, LocalDateTime end);

    @Query("select new ru.practicum.stats.dto.ViewStatsDto(s.app, s.uri, count(s.ip) as hits) " +
            " from EndpointHit as s " +
            " where s.times between ?1 and ?2 " +
            " group by s.app, s.uri " +
            " order by hits desc ")
    List<ViewStatsDto> getAllByTime(LocalDateTime start, LocalDateTime end);

    @Query("select new ru.practicum.stats.dto.ViewStatsDto(s.app, s.uri, count(s.ip) as hits) " +
            " from EndpointHit as s " +
            " where s.uri in (?1) " +
            " and s.times between ?2 and ?3 " +
            " group by s.app, s.uri " +
            " order by hits desc ")
    List<ViewStatsDto> getAllByTimeAndList(List<String> uriList, LocalDateTime start, LocalDateTime end);
}
