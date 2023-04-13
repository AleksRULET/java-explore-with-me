package ru.practicum.ewm.comment.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.ewm.event.model.dto.EventShortResponseDto;
import ru.practicum.ewm.user.model.dto.UserShortResponseDto;
import ru.practicum.ewm.util.constant.DateFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CommentResponseDto {

    private Long id;

    private String message;

    @JsonFormat(pattern = DateFormat.PATTERN)
    private LocalDateTime created;

    private EventShortResponseDto event;

    private UserShortResponseDto user;
}
