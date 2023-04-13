package ru.practicum.ewm.comment.sevice;

import ru.practicum.ewm.comment.model.dto.CommentRequestDto;
import ru.practicum.ewm.comment.model.dto.CommentResponseDto;

public interface UserCommentService {
    CommentResponseDto add(Long userId, Long eventId, CommentRequestDto commentRequestDto);

    CommentResponseDto edit(Long userId, Long commentId, CommentRequestDto newCommentDto);

    void remove(Long userId, Long commentId);
}
