package ru.practicum.ewm.compilation.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UpdateCompilationRequest {
    private Set<Long> events;
    private boolean pinned;
    private String title;
}
