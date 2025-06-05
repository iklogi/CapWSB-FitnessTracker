package pl.wsb.fitnesstracker.user.internal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.wsb.fitnesstracker.user.api.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository,UserMapper userMapper) {
        this.userRepository=userRepository;
        this.userMapper=userMapper;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long id) {
        Optional<User> maybe=userRepository.findById(id);
        return maybe.map(userMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
    }

    @Override
    public UserDto createUser(UserDto dto) {
        User u=userMapper.toEntity(dto);
        User saved=userRepository.save(u);
        return userMapper.toDto(saved);
    }

    @Override
    public UserDto updateUser(Long id, UserDto dto) {
        User existing=userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
        existing.setFirstName(dto.firstName());
        existing.setLastName(dto.lastName());
        existing.setBirthdate(dto.birthdate());
        existing.setEmail(dto.email());
        User updated=userRepository.save(existing);
        return userMapper.toDto(updated);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<BasicUserEmailDto> searchByEmail(String fragment) {
        return userRepository.findAllByEmailContainingIgnoreCase(fragment).stream()
                .map(u -> new BasicUserEmailDto(u.getId(),u.getEmail()))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> searchByAgeGreaterThan(int age) {
        LocalDate threshold=LocalDate.now().minusYears(age);
        return userRepository.findAllByBirthdateBefore(threshold).stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }
}
