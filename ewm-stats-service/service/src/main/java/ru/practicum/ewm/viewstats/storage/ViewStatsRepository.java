package ru.practicum.ewm.viewstats.storage;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.hit.model.Hit;
import ru.practicum.ewm.viewstats.model.ViewStats;

@Repository
public interface ViewStatsRepository extends JpaRepository<Hit, Long> {

    @Query("SELECT new ru.practicum.ewm.viewstats.model.ViewStats(v.app, v.uri, COUNT(v.ip)) " +
            "FROM Hit AS v " +
            "WHERE v.timestamp BETWEEN :start AND :end " +
            "GROUP BY v.app, v.uri " +
            "ORDER BY COUNT(v.ip) DESC")
    List<ViewStats> findViewStats(@Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

    @Query("SELECT new ru.practicum.ewm.viewstats.model.ViewStats(v.app, v.uri, COUNT(DISTINCT v.ip)) "
            +
            "FROM Hit AS v " +
            "WHERE v.timestamp BETWEEN :start AND :end " +
            "AND v.uri IN :uris " +
            "GROUP BY v.app, v.uri " +
            "ORDER BY COUNT(DISTINCT v.ip) DESC")
    List<ViewStats> findUniqueUriViewStats(@Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end, @Param("uris") List<String> uris);

    @Query("SELECT new ru.practicum.ewm.viewstats.model.ViewStats(v.app, v.uri, COUNT(v.ip)) " +
            "FROM Hit AS v " +
            "WHERE v.timestamp BETWEEN :start AND :end " +
            "AND v.uri IN :uris " +
            "GROUP BY v.app, v.uri " +
            "ORDER BY COUNT(v.ip) DESC")
    List<ViewStats> findUrisViewStats(@Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end, @Param("uris") List<String> uris);

    @Query("SELECT new ru.practicum.ewm.viewstats.model.ViewStats(v.app, v.uri, COUNT(DISTINCT v.ip)) "
            +
            "FROM Hit AS v " +
            "WHERE v.timestamp BETWEEN :start AND :end " +
            "GROUP BY v.app, v.uri " +
            "ORDER BY COUNT(DISTINCT v.ip) DESC")
    List<ViewStats> findUniqueViewStats(@Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);
}