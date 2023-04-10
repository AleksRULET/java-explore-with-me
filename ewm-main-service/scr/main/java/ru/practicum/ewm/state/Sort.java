package ru.practicum.ewm.state;

import java.util.Optional;

public enum Sort {
    EVENT_DATE,
    VIEWS;
    public static Optional<Sort> of(String str) {
        for (Sort sort : values()) {
            if (sort.name().equals(str)) {
                return Optional.of(sort);
            }
        }
        return Optional.empty();
    }
}
