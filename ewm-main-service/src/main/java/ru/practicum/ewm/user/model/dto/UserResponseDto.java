package ru.practicum.ewm.user.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserResponseDto {

    private Long id;
    private String email;
    private String name;
}
