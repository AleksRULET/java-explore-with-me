package ru.practicum.ewm.request.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.ewm.state.Status;
import ru.practicum.ewm.util.constant.DateFormat;

@Getter
@Setter
@ToString
public class ParticipationRequestResponseDto {

    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateFormat.PATTERN)
    private LocalDateTime created;
    private Long event;
    private Long requester;
    private Status status = Status.PENDING;


}
