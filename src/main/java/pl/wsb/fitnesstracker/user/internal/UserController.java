package pl.wsb.fitnesstracker.user.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import pl.wsb.fitnesstracker.user.api.User;


@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @PostMapping
    public UserDto addUser(@RequestBody UserDto userDto) throws InterruptedException {

        User user = userMapper.toEntity(userDto);
        User savedUser = userService.createUser(user);
        return userMapper.toDto(savedUser);


    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUser(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/search/email")
    public List<UserDto> findByEmailFragment(@RequestParam String query) {
        return userService.findUsersByEmailFragment(query)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @GetMapping("/older-than/{age}")
    public List<UserDto> getUsersOlderThan(@PathVariable int age) {
        return userService.findUsersOlderThan(age).stream()
                .map(userMapper::toDto)
                .toList();
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        return userMapper.toDto(userService.updateUser(id, userDto));
    }
    @GetMapping("/basic")
    public List<BasicUserDto> getBasicUsers() {
        return userService.findAllUsers().stream()
                .map(userMapper::toBasicDto)
                .toList();
    }

}