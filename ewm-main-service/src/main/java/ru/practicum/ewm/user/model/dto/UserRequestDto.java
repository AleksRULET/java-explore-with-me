package ru.practicum.ewm.user.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserRequestDto {

    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String name;
}
