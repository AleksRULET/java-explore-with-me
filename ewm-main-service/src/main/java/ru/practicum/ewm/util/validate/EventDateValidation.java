package ru.practicum.ewm.util.validate;

import java.time.LocalDateTime;
import java.util.Objects;
import ru.practicum.ewm.error.exceptions.EventConditionNotMetException;

public class EventDateValidation {

    private static final int MIN_DELTA_HOURS_EVENT_DATE_CURRENT_DATE = 2;

    private static final int MIN_DELTA_HOURS_EVENT_DATE_PUBLISHED = 1;

    public static void validateEventCreateUpdateUser(LocalDateTime eventDate) {
        if (Objects.nonNull(eventDate) && !eventDate.isAfter(
                LocalDateTime.now().plusHours(MIN_DELTA_HOURS_EVENT_DATE_CURRENT_DATE))) {
            throw new EventConditionNotMetException(
                    String.format("Field: eventDate. Error: должно содержать дату, " +
                            "которая еще не наступила. Value: %s", eventDate));
        }
    }

    public static void validateEventUpdateAdmin(LocalDateTime eventDate, LocalDateTime publishOn) {
        if (eventDate.minusHours(MIN_DELTA_HOURS_EVENT_DATE_PUBLISHED).isBefore(publishOn)) {
            throw new EventConditionNotMetException(
                    String.format("Field: eventDate. Error: должно содержать дату, " +
                            "которая еще не наступила. Value: %s", eventDate));
        }
    }

    public static void validateEventUpdateAdmin(LocalDateTime eventDate) {
        if (eventDate.isBefore(LocalDateTime.now())) {
            throw new EventConditionNotMetException(
                    String.format("Field: eventDate. Error: должно содержать дату, " +
                            "которая еще не наступила. Value: %s", eventDate));
        }
    }
}
