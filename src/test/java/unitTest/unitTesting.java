package unitTest;
import com.example.transferfile.dto.AuthRequest;
import com.example.transferfile.enums.Role;
import com.example.transferfile.exception.AuthException;
import com.example.transferfile.exception.UserException;
import com.example.transferfile.model.User;
import com.example.transferfile.repository.UserRepository;
import com.example.transferfile.service.UserService;
import com.example.transferfile.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class unitTesting {
    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository, new BCryptPasswordEncoder(), null, null, null);
    }
    //register
    @Test
    public void testRegisterUser_Success() throws AuthException {
        // Arrange
        AuthRequest request = new AuthRequest("testUser", "password");

        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.empty());

        // Act
        User result = userService.registerUser(request);

        // Assert
        assertNotNull(result);
        assertEquals(request.getUsername(), result.getUsername());
        assertTrue(result.isActive());
        assertEquals(Role.ROLE_USER, result.getRole());
        verify(userRepository, times(1)).findByUsername(request.getUsername());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testDeactivateUserById_Success() throws UserException {
        // Arrange
        Long userId = 123L;
        User user = new User();
        user.setId(userId);
        user.setActive(true);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        userService.deactivateUserById(userId);

        // Assert
        assertFalse(user.isActive());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testDeactivateUserById_UserNotFound() {
        // Arrange
        Long userId = 123L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(UserException.class, () -> userService.deactivateUserById(userId));
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testDeactivateUserById_UserNotActive() {
        // Arrange
        Long userId = 123L;
        User user = new User();
        user.setId(userId);
        user.setActive(false);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act and Assert
        assertThrows(UserException.class, () -> userService.deactivateUserById(userId));
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).save(any(User.class));
    }
}


