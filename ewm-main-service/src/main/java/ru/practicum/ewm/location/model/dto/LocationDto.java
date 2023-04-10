package ru.practicum.ewm.location.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class LocationDto {
    private Float lat;
    private Float lon;
}
