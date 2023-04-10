package ru.practicum.ewm.event.service;

import ru.practicum.ewm.event.model.dto.EventShortDto;
import ru.practicum.ewm.util.parameters.PublicEventsParameters;
import ru.practicum.ewm.event.model.dto.EventFullDto;

import java.util.List;

public interface PublicEventService {
    List<EventShortDto> getEvents(PublicEventsParameters parameters, Integer from, Integer size);

    EventFullDto getEvent(Long eventId);
}
