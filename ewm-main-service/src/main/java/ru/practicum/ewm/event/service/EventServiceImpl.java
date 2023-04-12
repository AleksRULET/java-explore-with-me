package ru.practicum.ewm.event.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.storage.CategoryRepository;
import ru.practicum.ewm.client.Client;
import ru.practicum.ewm.error.exceptions.EntityNotFoundException;
import ru.practicum.ewm.error.exceptions.EventConditionNotMetException;
import ru.practicum.ewm.error.exceptions.ParticipationRequestParticipantLimitViolationException;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.dto.EventFullResponseDto;
import ru.practicum.ewm.event.model.dto.EventMapper;
import ru.practicum.ewm.event.model.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.event.model.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.event.model.dto.EventShortResponseDto;
import ru.practicum.ewm.event.model.dto.EventRequestDto;
import ru.practicum.ewm.event.model.dto.UpdateEventAdminRequest;
import ru.practicum.ewm.event.model.dto.UpdateEventUserRequest;
import ru.practicum.ewm.event.storage.EventRepository;
import ru.practicum.ewm.event.storage.Predicates;
import ru.practicum.ewm.request.model.ParticipationRequest;
import ru.practicum.ewm.request.model.RequestCounter;
import ru.practicum.ewm.request.model.dto.ParticipationRequestResponseDto;
import ru.practicum.ewm.request.model.dto.ParticipationRequestMapper;
import ru.practicum.ewm.request.storage.ParticipationRequestRepository;
import ru.practicum.ewm.state.State;
import ru.practicum.ewm.state.Status;
import ru.practicum.ewm.state.UpdateStatus;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.storage.UserRepository;
import ru.practicum.ewm.util.JsonPatch;
import ru.practicum.ewm.util.PageRequestWithOffset;
import ru.practicum.ewm.util.constant.UnlimitedParticipationLimit;
import ru.practicum.ewm.util.parameters.AdminEventsParameters;
import ru.practicum.ewm.util.parameters.PublicEventsParameters;
import ru.practicum.ewm.util.validate.EventDateValidation;
import ru.practicum.ewm.util.validate.StateValidation;

@Service
@Transactional
@RequiredArgsConstructor
public class EventServiceImpl implements AdminEventService, PublicEventService, UserEventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final JsonPatch jsonPatch;
    private final Client client;
    private final ParticipationRequestRepository participationRequestRepository;

    @Transactional
    @Override
    public EventFullResponseDto add(Long userId, EventRequestDto eventRequestDto) {
        User initiator = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Category category = categoryRepository.findById(eventRequestDto.getCategory())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Category with id=%s was not found",
                                eventRequestDto.getCategory())));
        Event event = EventMapper.toEvent(eventRequestDto, category, initiator);
        event.setState(State.PENDING);
        event.setCreatedOn(LocalDateTime.now());
        Event createdEvent = eventRepository.save(event);
        return EventMapper.toEventFullDto(createdEvent);
    }

    @Override
    public List<EventShortResponseDto> findEvents(Long userId, int from, int size) {
        userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Pageable pageable = PageRequestWithOffset.of(from, size);
        List<Event> events = eventRepository.findAllWithCategoryAndInitiator(userId, pageable);
        return events.stream()
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullResponseDto findEvent(Long userId, Long eventId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Event event = eventRepository.findWithCategoryAndInitiator(userId, eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event was not found"));
        return EventMapper.toEventFullDto(event);
    }

    @Transactional
    @Override
    public EventFullResponseDto updateEvent(Long userId, Long eventId,
            UpdateEventUserRequest updateEventUserRequest) {
        userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("User with id=%s was not found", userId)));
        Event event = eventRepository.findWithCategoryAndInitiator(userId, eventId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Event with id=%s was not found", eventId)));
        StateValidation.validateEventUpdateUser(event.getState());
        Long categoryId = updateEventUserRequest.getCategory();
        Category category = categoryId == null ? null : categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Category with id=%s was not found", categoryId)));
        Event patchEvent = EventMapper.toEvent(updateEventUserRequest, category);
        Event updatedEvent = jsonPatch.mergePatch(event, patchEvent, Event.class);
        updatedEvent = eventRepository.save(updatedEvent);
        return EventMapper.toEventFullDto(updatedEvent);
    }

    @Override
    public List<EventFullResponseDto> findEvents(AdminEventsParameters parameters, Integer from,
            Integer size) {
        Pageable pageable = PageRequestWithOffset.of(from, size);
        List<Event> events = eventRepository.findAll((event, query, builder) -> {
            event.fetch("initiator");
            event.fetch("category");
            return builder.and(
                    getFiltersAdminEvents(parameters, event, builder).toArray(new Predicate[]{}));
        }, pageable).getContent();
        List<EventFullResponseDto> eventFullResponseDtos = events.stream()
                .map(EventMapper::toEventFullDto)
                .collect(Collectors.toList());
        Map<Long, Long> eventHits = client.getHits(events, parameters.getRangeStart(),
                parameters.getRangeEnd());
        eventFullResponseDtos.forEach(ent -> ent.setViews(eventHits.getOrDefault(ent.getId(), 0L)));
        Map<Long, Long> eventConfReq = getConfirmedRequestsCount(events);
        eventFullResponseDtos.forEach(
                ent -> ent.setConfirmedRequests(eventConfReq.getOrDefault(ent.getId(), 0L)));
        return eventFullResponseDtos;
    }

    private List<Predicate> getFiltersAdminEvents(AdminEventsParameters parameters,
            Root<Event> root, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        if (parameters.getUsers() != null) {
            predicates.add(Predicates.hasInitiatorIn(root, parameters.getUsers()));
        }
        if (parameters.getStates() != null) {
            predicates.add(Predicates.hasStateIn(root, parameters.getStates()));
        }
        if (parameters.getCategories() != null) {
            predicates.add(Predicates.hasCategoryIn(root, parameters.getCategories()));
        }
        if (parameters.getRangeStart() != null) {
            predicates.add(Predicates.hasRangeStart(root, builder, parameters.getRangeStart()));
        }
        if (parameters.getRangeEnd() != null) {
            predicates.add(Predicates.hasRangeEnd(root, builder, parameters.getRangeEnd()));
        }
        return predicates;
    }

    @Transactional
    @Override
    public EventFullResponseDto updateEvent(Long eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        Event event = eventRepository.findWithCategoryAndInitiator(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));
        Long categoryId = updateEventAdminRequest.getCategory();
        Category category = categoryId == null ? null : categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        Event patchEvent = EventMapper.toEvent(updateEventAdminRequest, category);
        StateValidation.validateEventUpdateAdmin(event.getState(), patchEvent.getState());
        Event updatedEvent = jsonPatch.mergePatch(event, patchEvent, Event.class);
        EventDateValidation.validateEventUpdateAdmin(updatedEvent.getEventDate());
        if (updatedEvent.getState() == State.PUBLISHED
                || (updatedEvent.getState() == State.PENDING
                && (updatedEvent.getParticipantLimit()
                .equals(UnlimitedParticipationLimit.UNLIMITED_PARTICIPATION_LIMIT)
                || !updatedEvent.getRequestModeration()))
        ) {
            LocalDateTime publishOn = LocalDateTime.now();
            EventDateValidation.validateEventUpdateAdmin(updatedEvent.getEventDate(), publishOn);
            updatedEvent.setPublishedOn(publishOn);
        }
        updatedEvent = eventRepository.save(updatedEvent);
        return EventMapper.toEventFullDto(updatedEvent);
    }

    @Override
    public List<EventShortResponseDto> getEvents(PublicEventsParameters parameters, Integer from,
            Integer size) {
        if (parameters.getSortType() == null) {
            return getEventsUnsorted(parameters, from, size);
        } else if (parameters.getSortType() == ru.practicum.ewm.state.Sort.EVENT_DATE) {
            return getEventsSortedEventDate(parameters, from, size);
        } else {
            return getEventsSortedViews(parameters, from, size);
        }
    }

    private List<EventShortResponseDto> getEvents(PublicEventsParameters parameters, Pageable pageable) {
        List<Event> events = eventRepository.findAll((event, query, builder) -> {
            event.fetch("initiator");
            event.fetch("category");
            return builder.and(
                    getFiltersPublicEvents(parameters, event, builder).toArray(new Predicate[]{}));
        }, pageable).getContent();
        Map<Long, Long> eventHits = client.getHits(events, parameters.getRangeStart(),
                parameters.getRangeEnd());
        List<EventShortResponseDto> eventShortResponseDtos = events.stream()
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toList());
        eventShortResponseDtos.forEach(ent -> ent.setViews(eventHits.getOrDefault(ent.getId(), 0L)));
        Map<Long, Long> eventConfReq = getConfirmedRequestsCount(events);
        eventShortResponseDtos.forEach(
                ent -> ent.setConfirmedRequests(eventConfReq.getOrDefault(ent.getId(), 0L)));
        if (parameters.getOnlyAvailable()) {
            eventShortResponseDtos = eventShortResponseDtos.stream()
                    .filter(ent -> ent.getConfirmedRequests()
                            .equals(ent.getParticipantLimit().longValue()))
                    .collect(Collectors.toList());
        }
        return eventShortResponseDtos;
    }

    private Map<Long, Long> getConfirmedRequestsCount(List<Event> events) {
        List<Long> eventsIds = events.stream()
                .map(Event::getId)
                .collect(Collectors.toList());
        List<RequestCounter> requestConfCount = participationRequestRepository
                .countRequestsForEvents(eventsIds, List.of(Status.CONFIRMED));
        return requestConfCount.stream()
                .collect(Collectors.toMap(RequestCounter::getEventId, RequestCounter::getCount,
                        (existing, replacement) -> existing));
    }

    private List<EventShortResponseDto> getEventsUnsorted(PublicEventsParameters parameters, Integer from,
            Integer size) {
        Pageable pageable = PageRequestWithOffset.of(from, size);
        return getEvents(parameters, pageable);
    }

    private List<EventShortResponseDto> getEventsSortedEventDate(PublicEventsParameters parameters,
            Integer from, Integer size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "eventDate");
        Pageable pageable = PageRequestWithOffset.of(from, size, sort);
        return getEvents(parameters, pageable);
    }

    private List<EventShortResponseDto> getEventsSortedViews(PublicEventsParameters parameters,
            Integer from, Integer size) {
        Pageable pageable = PageRequestWithOffset.of(from, size);
        List<EventShortResponseDto> events = getEvents(parameters, pageable);
        return events.stream()
                .sorted(Comparator.comparing(EventShortResponseDto::getViews))
                .collect(Collectors.toList());
    }

    private List<Predicate> getFiltersPublicEvents(PublicEventsParameters parameters,
            Root<Event> root, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(Predicates.hasStatusPublished(root, builder));
        if (parameters.getText() != null) {
            predicates.add(Predicates.hasTextIgnoreCase(root, builder, parameters.getText()));
        }
        if (parameters.getCategories() != null) {
            predicates.add(Predicates.hasCategoryIn(root, parameters.getCategories()));
        }
        if (parameters.getPaid() != null) {
            predicates.add(Predicates.hasPaid(root, builder, parameters.getPaid()));
        }
        if (parameters.getRangeStart() != null) {
            predicates.add(Predicates.hasRangeStart(root, builder, parameters.getRangeStart()));
        } else {
            predicates.add(Predicates.hasRangeStart(root, builder, LocalDateTime.now()));
        }
        if (parameters.getRangeEnd() != null) {
            predicates.add(Predicates.hasRangeEnd(root, builder, parameters.getRangeEnd()));
        }
        return predicates;
    }

    @Override
    public EventFullResponseDto getEvent(Long eventId) {
        Event event = eventRepository.findOne((eventRoot, query, builder) -> {
            eventRoot.fetch("initiator");
            eventRoot.fetch("category");
            return builder.and(Predicates.hasStatusPublished(eventRoot, builder),
                    Predicates.hasEventId(eventRoot, builder, eventId));
        }).orElseThrow(() -> new EntityNotFoundException(
                String.format("Event with id=%s was not found", eventId)));
        EventFullResponseDto eventFullResponseDto = EventMapper.toEventFullDto(event);
        eventFullResponseDto.setViews(client.getHit(eventFullResponseDto.getId()));
        eventFullResponseDto.setConfirmedRequests(getConfirmedRequestsCount(event));
        return eventFullResponseDto;
    }

    private Long getConfirmedRequestsCount(Event event) {
        return participationRequestRepository
                .countAllByStatusInAndEvent_Id(List.of(Status.CONFIRMED), event.getId());
    }

    @Override
    public List<ParticipationRequestResponseDto> getParticipationRequests(Long userId, Long eventId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("User with id=%s was not found", userId)));
        eventRepository.findWithCategoryAndInitiator(userId, eventId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Event with id=%s was not found", eventId)));
        List<ParticipationRequest> participationRequests = participationRequestRepository.findAllWithEvent(
                eventId);
        return participationRequests.stream()
                .map(ParticipationRequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public EventRequestStatusUpdateResult updateRequestStatus(Long userId, Long eventId,
            EventRequestStatusUpdateRequest updateRequest) {
        userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("User with id=%s was not found", userId)));
        Event event = eventRepository.findWithCategoryAndInitiator(userId, eventId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Event with id=%s was not found", eventId)));
        List<ParticipationRequest> participationRequests = participationRequestRepository
                .findAllWithEventAndRequester(eventId, updateRequest.getRequestIds());
        if (updateRequest.getStatus() == UpdateStatus.CONFIRMED) {
            return updateRequestStatusConfirm(event, participationRequests);
        } else {
            return updateRequestStatusCancel(participationRequests);
        }
    }

    private EventRequestStatusUpdateResult updateRequestStatusConfirm(Event event,
            List<ParticipationRequest> participationRequests) {
        if (event.getParticipantLimit()
                .equals(UnlimitedParticipationLimit.UNLIMITED_PARTICIPATION_LIMIT)
                || !event.getRequestModeration()) {
            List<ParticipationRequestResponseDto> participationRequestResponseDtos = participationRequests.stream()
                    .map(ParticipationRequestMapper::toParticipationRequestDto)
                    .collect(Collectors.toList());
            EventRequestStatusUpdateResult eventRequestStatusUpdateResult = new EventRequestStatusUpdateResult();
            eventRequestStatusUpdateResult.setConfirmedRequests(participationRequestResponseDtos);
            return eventRequestStatusUpdateResult;
        }
        int participationRequestConfirmedCount = participationRequestRepository
                .countAllByStatusInAndEvent_Id(List.of(Status.CONFIRMED), event.getId()).intValue();
        int availableParticipationLimit =
                event.getParticipantLimit() - participationRequestConfirmedCount;
        if (availableParticipationLimit == 0) {
            throw new ParticipationRequestParticipantLimitViolationException(
                    "The participant limit has been reached");
        }
        if (availableParticipationLimit < participationRequests.size()) {
            participationRequests = participationRequests.subList(0, availableParticipationLimit);
        }
        validateParticipationRequestStatus(participationRequests);
        participationRequests.forEach(pr -> pr.setStatus(Status.CONFIRMED));
        participationRequestRepository.saveAll(participationRequests);
        EventRequestStatusUpdateResult eventRequestStatusUpdateResult = new EventRequestStatusUpdateResult();
        List<ParticipationRequestResponseDto> confirmedRequests = participationRequests.stream()
                .map(ParticipationRequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
        eventRequestStatusUpdateResult.setConfirmedRequests(confirmedRequests);
        if (availableParticipationLimit == participationRequests.size()) {
            List<ParticipationRequest> rejectedParticipationRequests = participationRequestRepository
                    .findAllByEventAndStatusesFetch(event.getId(), List.of(Status.PENDING));
            rejectedParticipationRequests.forEach(pr -> pr.setStatus(Status.REJECTED));
            participationRequestRepository.saveAll(rejectedParticipationRequests);
            List<ParticipationRequestResponseDto> rejectedRequests = rejectedParticipationRequests.stream()
                    .map(ParticipationRequestMapper::toParticipationRequestDto)
                    .collect(Collectors.toList());
            eventRequestStatusUpdateResult.setRejectedRequests(rejectedRequests);
        }
        return eventRequestStatusUpdateResult;
    }

    private EventRequestStatusUpdateResult updateRequestStatusCancel(
            List<ParticipationRequest> participationRequests) {
        validateParticipationRequestStatus(participationRequests);
        participationRequests.forEach(pr -> pr.setStatus(Status.REJECTED));
        participationRequestRepository.saveAll(participationRequests);
        List<ParticipationRequestResponseDto> participationRequestResponseDtos = participationRequests.stream()
                .map(ParticipationRequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
        EventRequestStatusUpdateResult eventRequestStatusUpdateResult = new EventRequestStatusUpdateResult();
        eventRequestStatusUpdateResult.setRejectedRequests(participationRequestResponseDtos);
        return eventRequestStatusUpdateResult;
    }


    private void validateParticipationRequestStatus(
            List<ParticipationRequest> participationRequests) {
        participationRequests.stream()
                .filter(pr -> pr.getStatus() != Status.PENDING).findFirst()
                .ifPresent(pr -> {
                    throw new EventConditionNotMetException("Request must have status PENDING");
                });
    }
}
