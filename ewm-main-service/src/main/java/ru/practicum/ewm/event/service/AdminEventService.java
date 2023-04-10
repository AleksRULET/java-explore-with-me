package ru.practicum.ewm.event.service;

import ru.practicum.ewm.event.model.dto.EventFullDto;
import ru.practicum.ewm.event.model.dto.UpdateEventAdminRequest;
import ru.practicum.ewm.util.parameters.AdminEventsParameters;

import java.util.List;

public interface AdminEventService {
    List<EventFullDto> findEvents(AdminEventsParameters parameters, Integer from, Integer size);

    EventFullDto updateEvent(Long eventId, UpdateEventAdminRequest updateEventAdminRequest);
}
