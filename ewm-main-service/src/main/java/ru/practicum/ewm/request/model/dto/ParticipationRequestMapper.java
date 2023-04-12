package ru.practicum.ewm.request.model.dto;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.request.model.ParticipationRequest;

@Component
public class ParticipationRequestMapper {

    public static ParticipationRequestResponseDto toParticipationRequestDto(
            ParticipationRequest participationRequest) {
        ParticipationRequestResponseDto participationRequestResponseDto = new ParticipationRequestResponseDto();
        participationRequestResponseDto.setId(participationRequest.getId());
        participationRequestResponseDto.setCreated(participationRequest.getCreated());
        participationRequestResponseDto.setEvent(participationRequest.getEvent().getId());
        participationRequestResponseDto.setRequester(participationRequest.getRequester().getId());
        participationRequestResponseDto.setStatus(participationRequest.getStatus());
        return participationRequestResponseDto;
    }
}
