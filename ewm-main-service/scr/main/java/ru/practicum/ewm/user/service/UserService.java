package ru.practicum.ewm.user.service;

import ru.practicum.ewm.user.model.dto.NewUserRequest;
import ru.practicum.ewm.user.model.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> findUsers(List<Long> userId, int from, int size);

    UserDto add(NewUserRequest newUserRequest);

    void remove(Long userId);
}
