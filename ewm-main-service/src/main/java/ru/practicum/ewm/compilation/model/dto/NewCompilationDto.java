package ru.practicum.ewm.compilation.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class NewCompilationDto {
    private Set<Long> events;
    private boolean pinned;
    @NotBlank
    private String title;
}
