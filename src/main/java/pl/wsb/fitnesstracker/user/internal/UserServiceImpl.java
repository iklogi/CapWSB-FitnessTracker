package pl.wsb.fitnesstracker.user.internal;

import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.user.api.BasicUserEmailDto;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserDto;
import pl.wsb.fitnesstracker.user.api.UserService;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDto> getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto);
    }

    @Override
    public UserDto createUser(UserDto dto) {
        User entity = userMapper.fromDto(dto);
        User saved = userRepository.save(entity);
        return userMapper.toDto(saved);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<BasicUserEmailDto> searchByEmail(String email) {
        return userRepository
                .findAllByEmailContainingIgnoreCase(email)
                .stream()
                .map(userMapper::toBasicEmailDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> searchByAgeGreaterThan(int age) {
        LocalDate now = LocalDate.now();
        return userRepository.findAll().stream()
                .filter(u -> {
                    Period p = Period.between(u.getBirthdate(), now);
                    return p.getYears() > age;
                })
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }
}
