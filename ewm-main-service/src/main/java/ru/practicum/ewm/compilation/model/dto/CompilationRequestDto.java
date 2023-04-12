package ru.practicum.ewm.compilation.model.dto;

import java.util.Set;
import javax.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CompilationRequestDto {

    private Set<Long> events;
    private boolean pinned;
    @NotBlank
    private String title;
}
