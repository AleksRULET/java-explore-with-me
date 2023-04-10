package ru.practicum.ewm.request.model.dto;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.request.model.ParticipationRequest;

@Component
public class ParticipationRequestMapper {

    public ParticipationRequestDto toParticipationRequestDto(ParticipationRequest participationRequest) {
        ParticipationRequestDto participationRequestDto = new ParticipationRequestDto();
        participationRequestDto.setId(participationRequest.getId());
        participationRequestDto.setCreated(participationRequest.getCreated());
        participationRequestDto.setEvent(participationRequest.getEvent().getId());
        participationRequestDto.setRequester(participationRequest.getRequester().getId());
        participationRequestDto.setStatus(participationRequest.getStatus());
        return participationRequestDto;
    }
}
