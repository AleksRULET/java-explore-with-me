package ru.practicum.ewm.location.model.dto;

import ru.practicum.ewm.location.model.Location;

public class LocationMapper {

    public static LocationRequestDto toLocationDto(Location location) {
        LocationRequestDto locationRequestDto = new LocationRequestDto();
        locationRequestDto.setLat(location.getLat());
        locationRequestDto.setLon(location.getLon());
        return locationRequestDto;
    }

    public static Location toLocation(LocationRequestDto locationRequestDto) {
        Location location = new Location();
        location.setLat(locationRequestDto.getLat());
        location.setLon(locationRequestDto.getLon());
        return location;
    }
}
