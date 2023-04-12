package ru.practicum.ewm.request.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.error.exceptions.EntityNotFoundException;
import ru.practicum.ewm.error.exceptions.ParticipationRequestEventStatusViolationException;
import ru.practicum.ewm.error.exceptions.ParticipationRequestParticipantLimitViolationException;
import ru.practicum.ewm.error.exceptions.ParticipationRequestUserViolationException;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.storage.EventRepository;
import ru.practicum.ewm.request.model.ParticipationRequest;
import ru.practicum.ewm.request.model.dto.ParticipationRequestResponseDto;
import ru.practicum.ewm.request.model.dto.ParticipationRequestMapper;
import ru.practicum.ewm.request.storage.ParticipationRequestRepository;
import ru.practicum.ewm.state.State;
import ru.practicum.ewm.state.Status;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.storage.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class ParticipationRequestServiceImpl implements ParticipationRequestService {

    private static final int UNLIMITED_PARTICIPATION_LIMIT = 0;

    private final ParticipationRequestRepository participationRequestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public List<ParticipationRequestResponseDto> findParticipationRequests(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        List<ParticipationRequest> participationRequests = participationRequestRepository.findAllWithRequester(
                userId);
        return participationRequests.stream()
                .map(ParticipationRequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ParticipationRequestResponseDto addParticipationRequest(Long userId, Long eventId) {
        User requester = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Event event = eventRepository.findWithCategoryAndInitiator(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));
        if (userId.equals(event.getInitiator().getId())) {
            throw new ParticipationRequestUserViolationException(
                    "Event owner can't create participation request for that event");
        }
        if (event.getState() != State.PUBLISHED) {
            throw new ParticipationRequestEventStatusViolationException(
                    String.format("Participation request can't be " +
                                    "created for event id : %s state : %s" + event, eventId,
                            event.getState()));
        }
        List<ParticipationRequest> requestsUser = participationRequestRepository.findByRequesterAndEvent(
                requester, event);
        if (!requestsUser.isEmpty()) {
            throw new ParticipationRequestUserViolationException("Request already exist");
        }
        validateParticipationLimit(event);
        ParticipationRequest participationRequest = new ParticipationRequest();
        participationRequest.setRequester(requester);
        participationRequest.setEvent(event);
        participationRequest.setCreated(LocalDateTime.now());
        participationRequest.setStatus(calculateParticipationStatus(event));
        ParticipationRequest createdParticipationRequest = participationRequestRepository.save(
                participationRequest);
        return ParticipationRequestMapper.toParticipationRequestDto(createdParticipationRequest);
    }

    private void validateParticipationLimit(Event event) {
        if (event.getParticipantLimit().equals(UNLIMITED_PARTICIPATION_LIMIT)) {
            return;
        }
        List<Status> statusAcceptedRequests =
                event.getRequestModeration() ? List.of(Status.CONFIRMED)
                        : List.of(Status.CONFIRMED, Status.PENDING);
        int participationRequestConfirmedCount = participationRequestRepository
                .countAllByStatusInAndEvent_Id(statusAcceptedRequests, event.getId()).intValue();
        if (event.getParticipantLimit().equals(participationRequestConfirmedCount)) {
            throw new ParticipationRequestParticipantLimitViolationException(
                    "The participant limit has been reached");
        }
    }

    private Status calculateParticipationStatus(Event event) {
        if (!event.getRequestModeration()) {
            return Status.CONFIRMED;
        }
        if (event.getParticipantLimit().equals(UNLIMITED_PARTICIPATION_LIMIT)) {
            return Status.CONFIRMED;
        }
        return Status.PENDING;
    }

    @Transactional
    @Override
    public ParticipationRequestResponseDto cancelParticipationRequest(Long userId, Long requestId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        ParticipationRequest participationRequest = participationRequestRepository.findByIdAndRequester_Id(
                        requestId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Participation request not found"));
        participationRequest.setStatus(Status.CANCELED);
        ParticipationRequest updatedParticipationRequest = participationRequestRepository.save(
                participationRequest);
        return ParticipationRequestMapper.toParticipationRequestDto(updatedParticipationRequest);
    }
}
