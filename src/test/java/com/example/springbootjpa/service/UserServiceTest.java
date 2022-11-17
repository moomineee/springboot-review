package com.example.springbootjpa.service;

import com.example.springbootjpa.model.User;
import com.example.springbootjpa.model.dto.UserRequest;
import com.example.springbootjpa.model.dto.UserResponse;
import com.example.springbootjpa.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class UserServiceTest {
    //mockito를 통해 DI
    private UserRepository userRepository = Mockito.mock(UserRepository.class);

    private UserService userService;

    @BeforeEach()
    void setUp() {
        userService = new UserService(userRepository); // 수동 DI
    }

    @Test
    @DisplayName("회원 등록 성공 메세지가 잘 출력되는지")
    void addTest() {

        Mockito.when(userRepository.save(any()))
                .thenReturn(new User(1l, "moon", "1234"));
        // request도 수동으로
        UserResponse userResponse = userService.addUser(new UserRequest("moon", "1234"));
        assertEquals("moon", userResponse.getUsername());
        assertEquals("회원 등록 성공", userResponse.getMessage());
    }
}