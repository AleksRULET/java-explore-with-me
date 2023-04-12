package ru.practicum.ewm.event.service;

import java.util.List;
import ru.practicum.ewm.event.model.dto.EventFullResponseDto;
import ru.practicum.ewm.event.model.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.event.model.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.event.model.dto.EventShortResponseDto;
import ru.practicum.ewm.event.model.dto.EventRequestDto;
import ru.practicum.ewm.event.model.dto.UpdateEventUserRequest;
import ru.practicum.ewm.request.model.dto.ParticipationRequestResponseDto;

public interface UserEventService {

    EventFullResponseDto add(Long userId, EventRequestDto eventRequestDto);

    List<EventShortResponseDto> findEvents(Long userId, int from, int size);

    EventFullResponseDto findEvent(Long userId, Long eventId);

    EventFullResponseDto updateEvent(Long userId, Long eventId,
            UpdateEventUserRequest updateEventUserRequest);

    List<ParticipationRequestResponseDto> getParticipationRequests(Long userId, Long eventId);

    EventRequestStatusUpdateResult updateRequestStatus(Long userId, Long eventId,
            EventRequestStatusUpdateRequest updateRequest);
}
