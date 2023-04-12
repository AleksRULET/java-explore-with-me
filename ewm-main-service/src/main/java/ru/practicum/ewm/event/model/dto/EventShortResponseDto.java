package ru.practicum.ewm.event.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.ewm.category.model.dto.CategoryResponseDto;
import ru.practicum.ewm.user.model.dto.UserShortResponseDto;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class EventShortResponseDto {

    private Long id;
    private String annotation;
    private CategoryResponseDto category;
    private Long confirmedRequests;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private UserShortResponseDto initiator;
    private Boolean paid;
    private Integer participantLimit;
    private String title;
    private Long views;
}
