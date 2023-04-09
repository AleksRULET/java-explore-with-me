package ru.practicum.ewm.viewstats.service;

import ru.practicum.ewm.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ViewStatsServis {
    List<ViewStatsDto> findViewStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
