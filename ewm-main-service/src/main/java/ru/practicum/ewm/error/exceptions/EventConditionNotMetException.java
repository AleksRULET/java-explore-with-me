package ru.practicum.ewm.error.exceptions;

public class EventConditionNotMetException extends RuntimeException {

    public EventConditionNotMetException(String message) {
        super(message);
    }
}
