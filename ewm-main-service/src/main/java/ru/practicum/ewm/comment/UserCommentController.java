package ru.practicum.ewm.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comment.model.dto.CommentRequestDto;
import ru.practicum.ewm.comment.model.dto.CommentResponseDto;
import ru.practicum.ewm.comment.sevice.UserCommentService;

import javax.validation.Valid;

@RestController
@RequestMapping("/users/{userId}/comments")
@RequiredArgsConstructor
public class UserCommentController {

    private final UserCommentService userCommentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDto create(@PathVariable Long userId,
                                     @RequestParam Long eventId,
                                     @Valid @RequestBody CommentRequestDto commentRequestDto) {

        return userCommentService.add(userId, eventId, commentRequestDto);
    }

    @PatchMapping("/{commentId}")
    public CommentResponseDto update(@PathVariable Long userId,
                             @PathVariable Long commentId,
                             @Valid @RequestBody CommentRequestDto commentRequestDto) {
        return userCommentService.edit(userId, commentId, commentRequestDto);
    }

    @DeleteMapping("/{commentId}")
    public void delete(@PathVariable Long userId, @PathVariable Long commentId) {
        userCommentService.remove(userId, commentId);
    }
}
