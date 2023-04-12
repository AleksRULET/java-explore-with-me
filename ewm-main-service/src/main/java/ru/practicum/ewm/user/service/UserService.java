package ru.practicum.ewm.user.service;

import java.util.List;
import ru.practicum.ewm.user.model.dto.UserRequestDto;
import ru.practicum.ewm.user.model.dto.UserResponseDto;

public interface UserService {

    List<UserResponseDto> findUsers(List<Long> userId, int from, int size);

    UserResponseDto add(UserRequestDto userRequestDto);

    void remove(Long userId);
}
