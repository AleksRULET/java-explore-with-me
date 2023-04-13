package ru.practicum.ewm.comment.sevice;

import ru.practicum.ewm.comment.model.dto.CommentRequestDto;
import ru.practicum.ewm.comment.model.dto.CommentResponseDto;

public interface AdminCommentService {
    void remove(Long id);

    CommentResponseDto edit(Long commentId, CommentRequestDto commentRequestDto);
}
