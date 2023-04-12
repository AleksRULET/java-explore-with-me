package ru.practicum.ewm.location.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Embeddable
public class Location {

    @Column(name = "lat")
    private Float lat;

    @Column(name = "lon")
    private Float lon;
}
