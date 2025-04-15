package pl.wsb.fitnesstracker.user.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserProvider;
import pl.wsb.fitnesstracker.user.api.UserService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;

    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> findUsersByEmailFragment(String fragment) {
        return userRepository.findByEmailContainingIgnoreCase(fragment);
    }

    public List<User> findUsersOlderThan(int age) {
        LocalDate cutoffDate = LocalDate.now().minusYears(age);
        return userRepository.findAll().stream()
                .filter(user -> user.getBirthdate().isBefore(cutoffDate))
                .toList();
    }

    public User updateUser(Long id, UserDto dto) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        existing = new User(dto.firstName(), dto.lastName(), dto.birthdate(), dto.email());
        return userRepository.save(existing);
    }


}