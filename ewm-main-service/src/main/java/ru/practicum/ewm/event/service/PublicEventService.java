package ru.practicum.ewm.event.service;

import java.util.List;
import ru.practicum.ewm.event.model.dto.EventFullResponseDto;
import ru.practicum.ewm.event.model.dto.EventShortResponseDto;
import ru.practicum.ewm.util.parameters.PublicEventsParameters;

public interface PublicEventService {

    List<EventShortResponseDto> getEvents(PublicEventsParameters parameters, Integer from, Integer size);

    EventFullResponseDto getEvent(Long eventId);
}
