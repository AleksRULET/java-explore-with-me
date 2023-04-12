package ru.practicum.ewm.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comment.model.dto.CommentRequestDto;
import ru.practicum.ewm.comment.model.dto.CommentResponseDto;
import ru.practicum.ewm.comment.sevice.AdminCommentService;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/comments/")
@RequiredArgsConstructor
public class AdminCommentController {

    private final AdminCommentService adminCommentService;

    @DeleteMapping("{commentId}")
    public void delete(@PathVariable Long commentId) {
        adminCommentService.remove(commentId);
    }

    @PatchMapping("/{commentId}")
    public CommentResponseDto update(@PathVariable Long commentId,
                                     @Valid @RequestBody CommentRequestDto commentRequestDto) {
        return adminCommentService.edit(commentId, commentRequestDto);
    }
}
