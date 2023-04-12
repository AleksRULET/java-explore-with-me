package ru.practicum.ewm.request.service;

import java.util.List;
import ru.practicum.ewm.request.model.dto.ParticipationRequestResponseDto;

public interface ParticipationRequestService {

    List<ParticipationRequestResponseDto> findParticipationRequests(Long userId);

    ParticipationRequestResponseDto addParticipationRequest(Long userId, Long eventId);

    ParticipationRequestResponseDto cancelParticipationRequest(Long userId, Long requestId);
}
