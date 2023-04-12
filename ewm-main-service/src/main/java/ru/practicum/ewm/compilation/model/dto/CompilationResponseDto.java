package ru.practicum.ewm.compilation.model.dto;

import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.ewm.event.model.Event;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CompilationResponseDto {

    private Long id;
    private Set<Event> events;
    private Boolean pinned;
    private String title;
}
