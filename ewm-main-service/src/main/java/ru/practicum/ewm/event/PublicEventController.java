package ru.practicum.ewm.event;

import java.time.LocalDateTime;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.client.Client;
import ru.practicum.ewm.event.model.dto.EventFullResponseDto;
import ru.practicum.ewm.event.model.dto.EventShortResponseDto;
import ru.practicum.ewm.event.service.PublicEventService;
import ru.practicum.ewm.state.Sort;
import ru.practicum.ewm.util.constant.DateFormat;
import ru.practicum.ewm.util.parameters.PublicEventsParameters;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class PublicEventController {

    private final PublicEventService eventService;

    private final Client client;

    @GetMapping
    public List<EventShortResponseDto> findAll(@RequestParam(required = false) String text,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = DateFormat.PATTERN) LocalDateTime rangeStart,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = DateFormat.PATTERN) LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(required = false) String sort,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size,
            HttpServletRequest request) {
        PublicEventsParameters parameters;
        if (sort == null) {
            parameters = new PublicEventsParameters(text, categories, paid, rangeStart, rangeEnd,
                    onlyAvailable,
                    null);
        } else {
            Sort sortType = Sort.of(sort).orElseThrow(() -> new IllegalArgumentException(
                    "Failed to convert  String  to Sort"));
            parameters = new PublicEventsParameters(text, categories, paid, rangeStart, rangeEnd,
                    onlyAvailable,
                    sortType);
        }
        client.registerHit(request.getRemoteAddr(), request.getRequestURI());
        return eventService.getEvents(parameters, from, size);
    }

    @GetMapping("/{eventId}")
    public EventFullResponseDto find(@PathVariable Long eventId, HttpServletRequest request) {
        EventFullResponseDto eventFullResponseDto = eventService.getEvent(eventId);
        client.registerHit(request.getRemoteAddr(), request.getRequestURI());
        return eventFullResponseDto;
    }
}
