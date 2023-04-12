package ru.practicum.ewm.comment.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CommentRequestDto {

    @NotBlank
    @Size(max = 1000, message = "{validation.name.size.too_long}")
    private String message;

}
