package pl.wsb.fitnesstracker.user.internal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.user.api.BasicUserEmailDto;
import pl.wsb.fitnesstracker.user.api.UserDto;
import pl.wsb.fitnesstracker.user.api.UserNotFoundException;
import pl.wsb.fitnesstracker.user.api.UserService;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> getAll() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(dto -> ResponseEntity.ok(dto))
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @PostMapping
    public UserDto create(@RequestBody UserDto dto) {
        return userService.createUser(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/searchByEmail")
    public List<BasicUserEmailDto> searchByEmail(@RequestParam String email) {
        return userService.searchByEmail(email);
    }

    @GetMapping("/ageGreaterThan")
    public List<UserDto> searchByAge(@RequestParam int age) {
        return userService.searchByAgeGreaterThan(age);
    }
}
