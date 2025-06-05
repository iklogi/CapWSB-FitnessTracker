package pl.wsb.fitnesstracker.user.internal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.wsb.fitnesstracker.user.api.BasicUserEmailDto;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserDto;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceUnitTest {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userMapper = new UserMapper();
        userService = new UserServiceImpl(userRepository, userMapper);
    }

    @Test
    void createUser_andGetById() {
        User user = new User("Test", "User", LocalDate.of(2000, 1, 1), "test@example.com");
        User saved = new User("Test", "User", LocalDate.of(2000, 1, 1), "test@example.com");
        saved.setId(1L);
        when(userRepository.save(any(User.class))).thenReturn(saved);
        UserDto dto = new UserDto(null, "Test", "User", LocalDate.of(2000, 1, 1), "test@example.com");
        UserDto result = userService.createUser(dto);
        assertNotNull(result.id());
        assertEquals("Test", result.firstName());
    }

    @Test
    void deleteUser_delegatesToRepository() {
        doNothing().when(userRepository).deleteById(1L);
        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void searchByEmail_returnsBasicDtos() {
        User u = new User("A", "B", LocalDate.of(1990, 1, 1), "a@b.com");
        u.setId(5L);
        when(userRepository.findAllByEmailContainingIgnoreCase("a")).thenReturn(List.of(u));
        List<BasicUserEmailDto> list = userService.searchByEmail("a");
        assertEquals(1, list.size());
        assertEquals(5L, list.get(0).id());
    }
}
