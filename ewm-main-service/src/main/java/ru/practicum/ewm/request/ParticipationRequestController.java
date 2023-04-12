package ru.practicum.ewm.request;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.request.model.dto.ParticipationRequestResponseDto;
import ru.practicum.ewm.request.service.ParticipationRequestService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/requests")
public class ParticipationRequestController {

    private final ParticipationRequestService participationRequestService;

    @GetMapping
    public List<ParticipationRequestResponseDto> getAll(@PathVariable Long userId) {
        return participationRequestService.findParticipationRequests(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestResponseDto createParticipationRequest(@PathVariable Long userId,
            @RequestParam Long eventId) {
        return participationRequestService.addParticipationRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestResponseDto cancelParticipationRequest(@PathVariable Long userId,
            @PathVariable Long requestId) {
        return participationRequestService.cancelParticipationRequest(userId, requestId);
    }
}
