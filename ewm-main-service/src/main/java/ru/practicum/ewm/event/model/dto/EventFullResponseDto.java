package ru.practicum.ewm.event.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.ewm.category.model.dto.CategoryResponseDto;
import ru.practicum.ewm.location.model.dto.LocationRequestDto;
import ru.practicum.ewm.state.State;
import ru.practicum.ewm.user.model.dto.UserShortResponseDto;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class EventFullResponseDto {

    private Long id;
    private String annotation;
    private CategoryResponseDto category;
    private Long confirmedRequests;
    private LocalDateTime createdOn;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private LocationRequestDto location;
    private UserShortResponseDto initiator;
    private Boolean paid;
    private Integer participantLimit;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn;
    private Boolean requestModeration;
    private State state;
    private String title;
    private Long views;
}
