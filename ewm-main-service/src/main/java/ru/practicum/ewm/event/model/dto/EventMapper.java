package ru.practicum.ewm.event.model.dto;

import javax.annotation.Nullable;
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

    public static EventFullResponseDto toEventFullDto(Event event) {
        EventFullResponseDto eventFullResponseDto = new EventFullResponseDto();
        eventFullResponseDto.setEventDate(event.getEventDate());
        eventFullResponseDto.setAnnotation(event.getAnnotation());
        eventFullResponseDto.setTitle(event.getTitle());
        eventFullResponseDto.setDescription(event.getDescription());
        eventFullResponseDto.setCreatedOn(event.getCreatedOn());
        eventFullResponseDto.setState(event.getState());
        eventFullResponseDto.setPaid(event.getPaid());
        eventFullResponseDto.setParticipantLimit(event.getParticipantLimit());
        eventFullResponseDto.setRequestModeration(event.getRequestModeration());
        eventFullResponseDto.setPublishedOn(event.getPublishedOn());
        eventFullResponseDto.setId(event.getId());
        eventFullResponseDto.setCategory(CategoryMapper.toCategoryDto(event.getCategory()));
        eventFullResponseDto.setLocation(LocationMapper.toLocationDto(event.getLocation()));
        eventFullResponseDto.setInitiator(UserMapper.toUserShortDto(event.getInitiator()));
        return eventFullResponseDto;
    }

    public static EventShortResponseDto toEventShortDto(Event event) {
        EventShortResponseDto eventShortResponseDto = new EventShortResponseDto();
        eventShortResponseDto.setAnnotation(event.getAnnotation());
        eventShortResponseDto.setEventDate(event.getEventDate());
        eventShortResponseDto.setId(event.getId());
        eventShortResponseDto.setPaid(event.getPaid());
        eventShortResponseDto.setTitle(event.getTitle());
        eventShortResponseDto.setParticipantLimit(event.getParticipantLimit());
        eventShortResponseDto.setInitiator(UserMapper.toUserShortDto(event.getInitiator()));
        eventShortResponseDto.setCategory(CategoryMapper.toCategoryDto(event.getCategory()));
        return eventShortResponseDto;
    }

    public static Event toEvent(UpdateEventAdminRequest updateEventAdminRequest,
            @Nullable Category category) {
        Event event = new Event();
        event.setAnnotation(updateEventAdminRequest.getAnnotation());
        event.setCategory(category);
        event.setDescription(updateEventAdminRequest.getDescription());
        event.setEventDate(updateEventAdminRequest.getEventDate());
        event.setPaid(updateEventAdminRequest.getPaid());
        event.setParticipantLimit(updateEventAdminRequest.getParticipantLimit());
        event.setRequestModeration(updateEventAdminRequest.getRequestModeration());
        event.setState(State.of(updateEventAdminRequest.getStateAction()));
        event.setTitle(updateEventAdminRequest.getTitle());
        if (updateEventAdminRequest.getLocation() == null) {
            event.setLocation(null);
        } else {
            event.setLocation(LocationMapper.toLocation(updateEventAdminRequest.getLocation()));
        }
        return event;
    }

    public static Event toEvent(EventRequestDto eventRequestDto, Category category, User initiator) {
        Event event = new Event();
        event.setAnnotation(eventRequestDto.getAnnotation());
        event.setCategory(category);
        event.setDescription(eventRequestDto.getDescription());
        event.setEventDate(eventRequestDto.getEventDate());
        event.setLocation(LocationMapper.toLocation(eventRequestDto.getLocation()));
        event.setPaid(eventRequestDto.getPaid());
        event.setParticipantLimit(eventRequestDto.getParticipantLimit());
        event.setRequestModeration(eventRequestDto.getRequestModeration());
        event.setTitle(eventRequestDto.getTitle());
        event.setInitiator(initiator);
        return event;
    }

    public static Event toEvent(UpdateEventUserRequest updateEventUserRequest,
            @Nullable Category category) {
        Event event = new Event();
        event.setAnnotation(updateEventUserRequest.getAnnotation());
        event.setCategory(category);
        event.setDescription(updateEventUserRequest.getDescription());
        event.setEventDate(updateEventUserRequest.getEventDate());
        event.setPaid(updateEventUserRequest.getPaid());
        event.setParticipantLimit(updateEventUserRequest.getParticipantLimit());
        event.setState(State.of(updateEventUserRequest.getStateAction()));
        event.setTitle(updateEventUserRequest.getTitle());
        if (updateEventUserRequest.getLocation() == null) {
            event.setLocation(null);
        } else {
            event.setLocation(LocationMapper.toLocation(updateEventUserRequest.getLocation()));
        }
        return event;
    }
}
