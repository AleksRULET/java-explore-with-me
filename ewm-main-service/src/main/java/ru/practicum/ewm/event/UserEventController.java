package ru.practicum.ewm.event;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.model.dto.*;
import ru.practicum.ewm.event.service.UserEventService;
import ru.practicum.ewm.request.model.dto.ParticipationRequestDto;
import ru.practicum.ewm.util.validate.EventDateValidation;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/events")
public class UserEventController {
    private final UserEventService userEventService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto createEvent(@PathVariable Long userId,
                                    @Valid @RequestBody NewEventDto newEventDto) {
        EventDateValidation.validateEventCreateUpdateUser(newEventDto.getEventDate());
        return userEventService.add(userId, newEventDto);
    }

    @GetMapping
    public List<EventShortDto> findAll(@PathVariable Long userId,
                                       @RequestParam(defaultValue = "0") int from,
                                       @RequestParam(defaultValue = "10") int size) {
        return userEventService.findEvents(userId, from, size);
    }

    @GetMapping("/{eventId}")
    public EventFullDto find(@PathVariable Long userId,
                             @PathVariable Long eventId) {
        return userEventService.findEvent(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto update(@PathVariable Long userId,
                               @PathVariable Long eventId,
                               @Valid @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        EventDateValidation.validateEventCreateUpdateUser(updateEventUserRequest.getEventDate());
        return userEventService.updateEvent(userId, eventId, updateEventUserRequest);
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> findAll(@PathVariable Long userId, @PathVariable Long eventId) {
        return userEventService.getParticipationRequests(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult updateRequestStatus(@PathVariable Long userId,
                                                              @PathVariable Long eventId,
                                                              @Valid @RequestBody EventRequestStatusUpdateRequest updateRequest) {
        return userEventService.updateRequestStatus(userId, eventId, updateRequest);
    }
}
