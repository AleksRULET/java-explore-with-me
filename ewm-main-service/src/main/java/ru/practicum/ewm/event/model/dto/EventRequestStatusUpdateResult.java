package ru.practicum.ewm.event.model.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.ewm.request.model.dto.ParticipationRequestResponseDto;

@Getter
@Setter
@ToString
public class EventRequestStatusUpdateResult {

    private List<ParticipationRequestResponseDto> confirmedRequests;
    private List<ParticipationRequestResponseDto> rejectedRequests;
}
