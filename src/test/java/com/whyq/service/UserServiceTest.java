package com.whyq.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.whyq.dto.UserDTO;
import com.whyq.entity.User;
import com.whyq.repository.UserRepository;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_Success() {
        UserDTO userDTO = new UserDTO("test@example.com", "Test User", "password", "9876543210");
        when(userRepository.existsById(userDTO.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn("encodedPassword");

        userService.registerUser(userDTO);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUser_UserAlreadyExists() {
        UserDTO userDTO = new UserDTO("test@example.com", "Test User", "password", "9876543210");
        when(userRepository.existsById(userDTO.getEmail())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> userService.registerUser(userDTO));
    }

    @Test
    void testAuthenticateUser_Success() {
        User user = new User("test@example.com", "Test User", "encodedPassword", "9876543210");
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        when(passwordEncoder.matches("password", user.getPassword())).thenReturn(true);

        User authenticatedUser = userService.authenticateUser(user.getEmail(), "password");
        assertNotNull(authenticatedUser);
    }

    @Test
    void testAuthenticateUser_Failure() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(null);

        assertNull(userService.authenticateUser("test@example.com", "password"));
    }

    @Test
    void testGetUserNameByEmail_Success() {
        User user = new User("test@example.com", "Test User", "password", "9876543210");
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        String name = userService.getUserNameByEmail(user.getEmail());
        assertEquals("Test User", name);
    }

    @Test
    void testGetUserNameByEmail_UserNotFound() {
        when(userRepository.findByEmail("unknown@example.com")).thenReturn(null);

        assertThrows(RuntimeException.class, () -> userService.getUserNameByEmail("unknown@example.com"));
    }
}