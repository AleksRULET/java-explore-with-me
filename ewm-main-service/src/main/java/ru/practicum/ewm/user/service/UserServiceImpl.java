package ru.practicum.ewm.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import ru.practicum.ewm.error.exceptions.EntityNotFoundException;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.model.dto.NewUserRequest;
import ru.practicum.ewm.user.model.dto.UserDto;
import ru.practicum.ewm.user.model.dto.UserMapper;
import ru.practicum.ewm.user.storage.UserRepository;
import ru.practicum.ewm.util.PageRequestWithOffset;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserDto> findUsers(List<Long> userId, int from, int size) {
        Pageable pageable = PageRequestWithOffset.of(from, size);
        return userRepository.findByIdIn(userId, pageable)
                .stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto add(NewUserRequest newUserRequest) {
        User newUser = UserMapper.toUser(newUserRequest);
        User createdUser = userRepository.save(newUser);
        return UserMapper.toUserDto(createdUser);
    }

    @Override
    public void remove(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        userRepository.deleteById(userId);
    }
}
