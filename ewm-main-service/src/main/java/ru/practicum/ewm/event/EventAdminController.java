package ru.practicum.ewm.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.util.parameters.AdminEventsParameters;
import ru.practicum.ewm.state.State;
import ru.practicum.ewm.event.model.dto.EventFullDto;
import ru.practicum.ewm.event.model.dto.UpdateEventAdminRequest;
import ru.practicum.ewm.event.service.AdminEventService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/events")
public class EventAdminController {

    private final AdminEventService eventService;

    @GetMapping
    public List<EventFullDto> findAll(@RequestParam(required = false) List<Long> users,
                                      @RequestParam(required = false) List<String> states,
                                      @RequestParam(required = false) List<Long> categories,
                                      @RequestParam(required = false)
                                      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                      @RequestParam(required = false)
                                      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                      @RequestParam(defaultValue = "0") Integer from,
                                      @RequestParam(defaultValue = "10") Integer size) {
        AdminEventsParameters parameters;
        if (states == null) {
            parameters = new AdminEventsParameters(users, null, categories, rangeStart, rangeEnd);
        } else {
            parameters = new AdminEventsParameters(users, State.of(states), categories, rangeStart, rangeEnd);
        }
        return eventService.findEvents(parameters, from, size);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto update(@PathVariable Long eventId,
                               @RequestBody UpdateEventAdminRequest updateEventAdminRequest) {
        return eventService.updateEvent(eventId, updateEventAdminRequest);
    }
}
