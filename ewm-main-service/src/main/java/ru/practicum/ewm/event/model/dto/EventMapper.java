package ru.practicum.ewm.event.model.dto;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.model.dto.CategoryMapper;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.location.model.dto.LocationMapper;
import ru.practicum.ewm.state.State;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.model.dto.UserMapper;

@Component
public class EventMapper {

    public static EventFullDto toEventFullDto(Event event) {
        EventFullDto eventFullDto = new EventFullDto();
        eventFullDto.setEventDate(event.getEventDate());
        eventFullDto.setAnnotation(event.getAnnotation());
        eventFullDto.setTitle(event.getTitle());
        eventFullDto.setDescription(event.getDescription());
        eventFullDto.setCreatedOn(event.getCreatedOn());
        eventFullDto.setState(event.getState());
        eventFullDto.setPaid(event.getPaid());
        eventFullDto.setParticipantLimit(event.getParticipantLimit());
        eventFullDto.setRequestModeration(event.getRequestModeration());
        eventFullDto.setPublishedOn(event.getPublishedOn());
        eventFullDto.setId(event.getId());
        eventFullDto.setCategory(CategoryMapper.toCategoryDto(event.getCategory()));
        eventFullDto.setLocation(LocationMapper.toLocationDto(event.getLocation()));
        eventFullDto.setInitiator(UserMapper.toUserShortDto(event.getInitiator()));
        return eventFullDto;
    }

    public static EventShortDto toEventShortDto(Event event) {
        EventShortDto eventShortDto = new EventShortDto();
        eventShortDto.setAnnotation(event.getAnnotation());
        eventShortDto.setEventDate(event.getEventDate());
        eventShortDto.setId(event.getId());
        eventShortDto.setPaid(event.getPaid());
        eventShortDto.setTitle(event.getTitle());
        eventShortDto.setParticipantLimit(event.getParticipantLimit());
        eventShortDto.setInitiatorId(UserMapper.toUserShortDto(event.getInitiator()));
        eventShortDto.setCategory(CategoryMapper.toCategoryDto(event.getCategory()));
        return eventShortDto;
    }

    public static Event toEvent(UpdateEventAdminRequest updateEventAdminRequest, Category category) {
        Event event = new Event();
        event.setAnnotation(updateEventAdminRequest.getAnnotation());
        event.setCategory(category);
        event.setDescription(updateEventAdminRequest.getDescription());
        event.setEventDate(updateEventAdminRequest.getEventDate());
        event.setPaid(updateEventAdminRequest.getPaid());
        event.setParticipantLimit(updateEventAdminRequest.getParticipantLimit());
        event.setRequestModeration(updateEventAdminRequest.getRequestModeration());
        event.setState(State.of(updateEventAdminRequest.getAdminAction()));
        event.setTitle(updateEventAdminRequest.getTitle());
        event.setLocation(LocationMapper.toLocation(updateEventAdminRequest.getLocation()));
        return event;
    }

    public static Event toEvent(NewEventDto newEventDto, Category category, User initiator) {
        Event event = new Event();
        event.setAnnotation(newEventDto.getAnnotation());
        event.setCategory(category);
        event.setDescription(newEventDto.getDescription());
        event.setEventDate(newEventDto.getEventDate());
        event.setLocation(LocationMapper.toLocation(newEventDto.getLocation()));
        event.setPaid(newEventDto.getPaid());
        event.setParticipantLimit(newEventDto.getParticipantLimit());
        event.setRequestModeration(newEventDto.getRequestModeration());
        event.setTitle(newEventDto.getTitle());
        event.setInitiator(initiator);
        return event;
    }

    public static Event toEvent(UpdateEventUserRequest updateEventUserRequest, Category category) {
        Event event = new Event();
        event.setAnnotation(updateEventUserRequest.getAnnotation());
        event.setCategory(category);
        event.setDescription(updateEventUserRequest.getDescription());
        event.setEventDate(updateEventUserRequest.getEventDate());
        event.setPaid(updateEventUserRequest.getPaid());
        event.setParticipantLimit(updateEventUserRequest.getParticipantLimit());
        event.setState(State.of(updateEventUserRequest.getUserAction()));
        event.setTitle(updateEventUserRequest.getTitle());
        event.setLocation(LocationMapper.toLocation(updateEventUserRequest.getLocation()));
        return event;
    }
}
