package pl.wsb.fitnesstracker.user.api;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDto> getAllUsers();

    Optional<UserDto> getUserById(Long id);

    UserDto createUser(UserDto userDto);

    void deleteUser(Long id);

    List<BasicUserEmailDto> searchByEmail(String email);

    List<UserDto> searchByAgeGreaterThan(int age);
}
