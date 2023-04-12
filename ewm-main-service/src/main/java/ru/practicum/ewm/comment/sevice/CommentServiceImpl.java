package ru.practicum.ewm.comment.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.comment.model.Comment;
import ru.practicum.ewm.comment.model.dto.CommentMapper;
import ru.practicum.ewm.comment.model.dto.CommentRequestDto;
import ru.practicum.ewm.comment.model.dto.CommentResponseDto;
import ru.practicum.ewm.comment.storage.CommentRepository;
import ru.practicum.ewm.error.exceptions.ConditionNotMetException;
import ru.practicum.ewm.error.exceptions.EntityNotFoundException;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.storage.EventRepository;
import ru.practicum.ewm.state.State;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.storage.UserRepository;
import ru.practicum.ewm.util.PageRequestWithOffset;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements AdminCommentService, UserCommentService, PublicCommentsService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public CommentResponseDto add(Long userId, Long eventId, CommentRequestDto commentRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Event event = eventRepository.findWithCategoryAndInitiator(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));
        if (event.getState() != State.PUBLISHED) {
            throw new ConditionNotMetException("Event must have status published");
        }
        Comment comment = CommentMapper.toComment(commentRequestDto, user, event);
        comment.setCreated(LocalDateTime.now());
        return CommentMapper.commentResponseDto(commentRepository.save(comment));
    }

    @Override
    public CommentResponseDto edit(Long userId, Long commentId, CommentRequestDto commentRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Comment comment = commentRepository.findByIdAndUser(commentId, user)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        comment.setMessage(commentRequestDto.getMessage());
        return CommentMapper.commentResponseDto(commentRepository.save(comment));
    }

    @Override
    public void remove(Long userId, Long commentId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        commentRepository.findByIdAndUser(commentId, user)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        commentRepository.deleteById(commentId);
    }

    @Override
    public CommentResponseDto find(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        return CommentMapper.commentResponseDto(comment);
    }

    @Override
    public List<CommentResponseDto> findAll(Long eventId, Integer from, Integer size) {
        Pageable pageable = PageRequestWithOffset.of(from, size);
        List<Comment> comments = commentRepository.findAllById(eventId,  pageable).getContent();
        return comments.stream()
                .map(CommentMapper::commentResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void remove(Long commentId) {
        commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        commentRepository.deleteById(commentId);
    }

    @Override
    public CommentResponseDto edit(Long commentId, CommentRequestDto commentRequestDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        comment.setMessage(commentRequestDto.getMessage());
        return CommentMapper.commentResponseDto(commentRepository.save(comment));
    }
}
