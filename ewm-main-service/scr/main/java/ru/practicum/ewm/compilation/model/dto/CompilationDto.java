package ru.practicum.ewm.compilation.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.ewm.event.model.Event;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CompilationDto {
    private Long id;
    private Set<Event> events;
    private Boolean pinned;
    private String title;
}
