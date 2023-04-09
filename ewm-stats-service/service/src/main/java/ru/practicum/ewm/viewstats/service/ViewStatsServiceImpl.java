package ru.practicum.ewm.viewstats.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.ViewStatsDto;
import ru.practicum.ewm.viewstats.model.ViewMapper;
import ru.practicum.ewm.viewstats.storage.ViewStatsRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ViewStatsServiceImpl implements ViewStatsServis {
    private final ViewStatsRepository viewStatsRepository;

    @Override
    public List<ViewStatsDto> findViewStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (unique) {
            if (uris == null) {
                return viewStatsRepository.findUniqueViewStats(start, end).stream()
                        .map(ViewMapper::toViewStatsDto)
                        .collect(Collectors.toList());
            } else {
                return viewStatsRepository.findUniqueUriViewStats(start, end, uris).stream()
                        .map(ViewMapper::toViewStatsDto)
                        .collect(Collectors.toList());
            }
        } else {
            if (uris == null) {
                return viewStatsRepository.findViewStats(start, end).stream()
                        .map(ViewMapper::toViewStatsDto)
                        .collect(Collectors.toList());
            } else {
                return viewStatsRepository.findUrisViewStats(start, end, uris).stream()
                        .map(ViewMapper::toViewStatsDto)
                        .collect(Collectors.toList());
            }
        }
    }
}
