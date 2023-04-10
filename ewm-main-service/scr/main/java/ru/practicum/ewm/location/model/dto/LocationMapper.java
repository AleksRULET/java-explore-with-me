package ru.practicum.ewm.location.model.dto;

import ru.practicum.ewm.location.model.Location;

public class LocationMapper {
    public static LocationDto toLocationDto(Location location) {
        LocationDto locationDto = new LocationDto();
        locationDto.setLat(location.getLat());
        locationDto.setLon(locationDto.getLon());
        return locationDto;
    }

    public static Location toLocation(LocationDto locationDto) {
        Location location = new Location();
        location.setLat(locationDto.getLat());
        location.setLon(locationDto.getLon());
        return location;
    }
}
