package ru.practicum.ewm.event.service;

import ru.practicum.ewm.event.model.dto.*;
import ru.practicum.ewm.request.model.dto.ParticipationRequestDto;

import java.util.List;

public interface UserEventService {
    EventFullDto add(Long userId, NewEventDto newEventDto);

    List<EventShortDto> findEvents(Long userId, int from, int size);

    EventFullDto findEvent(Long userId, Long eventId);

    EventFullDto updateEvent(Long userId, Long eventId, UpdateEventUserRequest updateEventUserRequest);

    List<ParticipationRequestDto> getParticipationRequests(Long userId, Long eventId);

    EventRequestStatusUpdateResult updateRequestStatus(Long userId, Long eventId, EventRequestStatusUpdateRequest updateRequest);
}
