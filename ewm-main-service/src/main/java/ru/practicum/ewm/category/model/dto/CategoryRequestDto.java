package ru.practicum.ewm.category.model.dto;

import javax.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CategoryRequestDto {

    @NotBlank
    private String name;
}
