package ru.practicum.ewm.category.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CategoryDto {
    private Long id;
    private String name;
}
