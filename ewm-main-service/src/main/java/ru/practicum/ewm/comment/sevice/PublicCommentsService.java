package ru.practicum.ewm.comment.sevice;

import ru.practicum.ewm.comment.model.dto.CommentResponseDto;

import java.util.List;

public interface PublicCommentsService {
    CommentResponseDto find(Long commentId);

    List<CommentResponseDto> findAll(Long eventId, Integer from, Integer size);
}
