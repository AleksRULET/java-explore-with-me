package ru.practicum.ewm.event.service;

import java.util.List;
import ru.practicum.ewm.event.model.dto.EventFullResponseDto;
import ru.practicum.ewm.event.model.dto.UpdateEventAdminRequest;
import ru.practicum.ewm.util.parameters.AdminEventsParameters;

public interface AdminEventService {

    List<EventFullResponseDto> findEvents(AdminEventsParameters parameters, Integer from, Integer size);

    EventFullResponseDto updateEvent(Long eventId, UpdateEventAdminRequest updateEventAdminRequest);
}
