package ru.practicum.ewm.comment.model.dto;


import ru.practicum.ewm.comment.model.Comment;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.dto.EventMapper;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.model.dto.UserMapper;

public class CommentMapper {

    public static CommentResponseDto commentResponseDto(Comment comment) {

        CommentResponseDto responseDto = new CommentResponseDto();

        responseDto.setId(comment.getId());
        responseDto.setCreated(comment.getCreated());
        responseDto.setEvent(EventMapper.toEventShortDto(comment.getEvent()));
        responseDto.setMessage(comment.getMessage());
        responseDto.setUser(UserMapper.toUserShortDto(comment.getUser()));

        return responseDto;
    }

    public static Comment toComment(CommentRequestDto commentRequestDto, User user, Event event) {

        Comment comment = new Comment();

        comment.setMessage(commentRequestDto.getMessage());
        comment.setUser(user);
        comment.setEvent(event);

        return comment;

    }

}
