package ru.practicum.ewm.event.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.ewm.location.model.dto.LocationRequestDto;
import ru.practicum.ewm.state.AdminAction;
import ru.practicum.ewm.util.constant.DateFormat;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UpdateEventAdminRequest {

    private String annotation;
    private Long category;
    private String description;
    @JsonFormat(pattern = DateFormat.PATTERN)
    private LocalDateTime eventDate;
    private LocationRequestDto location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private AdminAction stateAction;
    private String title;
}
