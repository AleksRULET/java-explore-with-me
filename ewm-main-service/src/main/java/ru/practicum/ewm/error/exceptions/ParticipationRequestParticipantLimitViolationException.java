package ru.practicum.ewm.error.exceptions;

public class ParticipationRequestParticipantLimitViolationException extends RuntimeException {

    public ParticipationRequestParticipantLimitViolationException(String message) {
        super(message);
    }
}
