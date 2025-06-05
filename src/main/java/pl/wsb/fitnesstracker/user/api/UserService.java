package pl.wsb.fitnesstracker.user.api;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto getUserById(Long id);
    UserDto createUser(UserDto dto);
    UserDto updateUser(Long id, UserDto dto);
}