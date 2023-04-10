package ru.practicum.ewm.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.user.model.dto.NewUserRequest;
import ru.practicum.ewm.user.model.dto.UserDto;
import ru.practicum.ewm.user.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> getUsers(@RequestParam List<Long> ids,
                                  @RequestParam(defaultValue = "0") int from,
                                  @RequestParam(defaultValue = "10") int size) {
        return userService.findUsers(ids, from, size);
    }

    @PostMapping
    public UserDto creatUser(@RequestBody NewUserRequest newUserRequest) {
        return userService.add(newUserRequest);
    }

    @DeleteMapping("/{userid}")
    public void deleteUser(@PathVariable Long userid) {
        userService.remove(userid);
    }

}
