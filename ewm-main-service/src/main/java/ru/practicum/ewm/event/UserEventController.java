package ru.practicum.ewm.event;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.event.model.dto.EventFullResponseDto;
import ru.practicum.ewm.event.model.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.event.model.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.event.model.dto.EventShortResponseDto;
import ru.practicum.ewm.event.model.dto.EventRequestDto;
import ru.practicum.ewm.event.model.dto.UpdateEventUserRequest;
import ru.practicum.ewm.event.service.UserEventService;
import ru.practicum.ewm.request.model.dto.ParticipationRequestResponseDto;
import ru.practicum.ewm.util.validate.EventDateValidation;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/events")
public class UserEventController {

    private final UserEventService userEventService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullResponseDto createEvent(@PathVariable Long userId,
            @Valid @RequestBody EventRequestDto eventRequestDto) {
        EventDateValidation.validateEventCreateUpdateUser(eventRequestDto.getEventDate());
        return userEventService.add(userId, eventRequestDto);
    }

    @GetMapping
    public List<EventShortResponseDto> findAll(@PathVariable Long userId,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int size) {
        return userEventService.findEvents(userId, from, size);
    }

    @GetMapping("/{eventId}")
    public EventFullResponseDto find(@PathVariable Long userId,
            @PathVariable Long eventId) {
        return userEventService.findEvent(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullResponseDto update(@PathVariable Long userId,
            @PathVariable Long eventId,
            @Valid @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        EventDateValidation.validateEventCreateUpdateUser(updateEventUserRequest.getEventDate());
        return userEventService.updateEvent(userId, eventId, updateEventUserRequest);
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestResponseDto> findAll(@PathVariable Long userId,
            @PathVariable Long eventId) {
        return userEventService.getParticipationRequests(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult updateRequestStatus(@PathVariable Long userId,
            @PathVariable Long eventId,
            @Valid @RequestBody EventRequestStatusUpdateRequest updateRequest) {
        return userEventService.updateRequestStatus(userId, eventId, updateRequest);
    }
}
