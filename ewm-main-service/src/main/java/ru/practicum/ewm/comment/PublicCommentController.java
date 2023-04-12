package ru.practicum.ewm.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comment.model.dto.CommentResponseDto;
import ru.practicum.ewm.comment.sevice.PublicCommentsService;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class PublicCommentController {

    private final PublicCommentsService commentService;

    @GetMapping("/{commentId}")
    public CommentResponseDto get(@PathVariable Long commentId) {
        return commentService.find(commentId);
    }

    @GetMapping
    public List<CommentResponseDto> getAll(@RequestParam Long eventId,
                                           @RequestParam(defaultValue = "0") Integer from,
                                           @RequestParam(defaultValue = "10") Integer size) {
        return commentService.findAll(eventId, from, size);
    }
}
