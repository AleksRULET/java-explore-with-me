package ru.practicum.ewm.event;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.event.model.dto.EventFullResponseDto;
import ru.practicum.ewm.event.model.dto.UpdateEventAdminRequest;
import ru.practicum.ewm.event.service.AdminEventService;
import ru.practicum.ewm.state.State;
import ru.practicum.ewm.util.constant.DateFormat;
import ru.practicum.ewm.util.parameters.AdminEventsParameters;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/events")
public class EventAdminController {

    private final AdminEventService eventService;

    @GetMapping
    public List<EventFullResponseDto> findAll(@RequestParam(required = false) List<Long> users,
            @RequestParam(required = false) List<String> states,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = DateFormat.PATTERN) LocalDateTime rangeStart,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = DateFormat.PATTERN) LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size) {
        AdminEventsParameters parameters;
        if (states == null) {
            parameters = new AdminEventsParameters(users, null, categories, rangeStart, rangeEnd);
        } else {
            parameters = new AdminEventsParameters(users, State.of(states), categories, rangeStart,
                    rangeEnd);
        }
        return eventService.findEvents(parameters, from, size);
    }

    @PatchMapping("/{eventId}")
    public EventFullResponseDto update(@PathVariable Long eventId,
            @RequestBody UpdateEventAdminRequest updateEventAdminRequest) {
        return eventService.updateEvent(eventId, updateEventAdminRequest);
    }
}
