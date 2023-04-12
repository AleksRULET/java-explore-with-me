package ru.practicum.ewm.viewstats.model;

import ru.practicum.ewm.dto.ViewStatsDto;

public class ViewMapper {

    public static ViewStatsDto toViewStatsDto(ViewStats viewStats) {
        ViewStatsDto viewStatsDto = new ViewStatsDto();
        viewStatsDto.setApp(viewStats.getApp());
        viewStatsDto.setUri(viewStats.getUri());
        viewStatsDto.setHits(viewStats.getHits());
        return viewStatsDto;
    }
}
