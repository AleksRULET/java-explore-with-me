package ru.practicum.ewm.state;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum State {
    PENDING,
    PUBLISHED,
    CANCELED;

    public static State of(UserAction userAction) {
        if (userAction == null) {
            return null;
        }
        if (userAction == userAction.CANCEL_REVIEW) {
            return CANCELED;
        } else {
            return PENDING;
        }
    }

    public static State of(AdminAction adminAction) {
        if (adminAction == null) {
            return null;
        }
        if (adminAction == adminAction.REJECT_EVENT) {
            return CANCELED;
        } else {
            return PUBLISHED;
        }
    }

    public static Optional<State> of(String str) {
        for (State state : values()) {
            if (state.name().equals(str)) {
                return Optional.of(state);
            }
        }
        return Optional.empty();
    }

    public static List<State> of(List<String> strings) {
        return strings.stream()
                .map(str -> of(str).orElseThrow(
                        () -> new IllegalArgumentException(
                                String.format("Failed to convert value '%s' of type String  to State", str))))
                .collect(Collectors.toList());
    }
}
