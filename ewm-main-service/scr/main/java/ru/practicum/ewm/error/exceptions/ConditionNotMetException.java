package ru.practicum.ewm.error.exceptions;

public class ConditionNotMetException extends RuntimeException {

    public ConditionNotMetException(String message) {
        super(message);
    }
}
