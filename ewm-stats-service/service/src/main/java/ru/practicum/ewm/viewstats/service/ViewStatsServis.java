package ru.practicum.ewm.viewstats.service;

import java.time.LocalDateTime;
import java.util.List;
import ru.practicum.ewm.dto.ViewStatsDto;

public interface ViewStatsServis {

    List<ViewStatsDto> findViewStats(LocalDateTime start, LocalDateTime end, List<String> uris,
            Boolean unique);
}
