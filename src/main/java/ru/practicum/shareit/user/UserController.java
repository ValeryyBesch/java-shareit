package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserServiceImpl;

import javax.validation.Valid;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping
    public UserDto addUser(@RequestBody @Valid UserDto userDto) {
        return userService.addUser(userDto);
    }

    @PatchMapping("/{userId}")
    public UserDto updateUser(@RequestBody UserDto userDto, @PathVariable Long userId) {
        return userService.updateUser(userDto, userId);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
   }
}
