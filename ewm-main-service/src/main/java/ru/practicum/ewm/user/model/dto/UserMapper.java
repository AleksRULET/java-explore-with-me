package ru.practicum.ewm.user.model.dto;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.user.model.User;

@Component
public class UserMapper {

    public static UserResponseDto toUserDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setId(user.getId());
        userResponseDto.setName(user.getName());
        return userResponseDto;
    }

    public static UserShortResponseDto toUserShortDto(User user) {
        UserShortResponseDto userShortResponseDto = new UserShortResponseDto();
        userShortResponseDto.setId(user.getId());
        userShortResponseDto.setName(user.getName());
        return userShortResponseDto;
    }

    public static User toUser(UserRequestDto userRequestDto) {
        User user = new User();
        user.setEmail(userRequestDto.getEmail());
        user.setName(userRequestDto.getName());
        return user;
    }
}
