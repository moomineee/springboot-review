package com.example.springbootjpa.controller;

import com.example.springbootjpa.model.dto.UserResponse;
import com.example.springbootjpa.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(UserRestController.class)
public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Test
    @DisplayName("입력한 id로 조회가 잘 되는지")
    void findById() throws Exception {

        given(userService.getUser(1l)).willReturn(new UserResponse(1l, "moon", "회원 등록 성공"));
        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.message").value("회원 등록 성공"))
                .andDo(print());
    }

    @Test
    @DisplayName("입력한 Id로 조회를 실패했을 경우의 메세지 출력")
    void findByIdFail() throws Exception {
        given(userService.getUser(2l)).willReturn(new UserResponse(null, "", "해당 Id의 유저가 없습니다"));
        mockMvc.perform(get("/api/v1/users/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isEmpty())
                .andExpect(jsonPath("$.message").value("해당 Id의 유저가 없습니다"))
                .andDo(print());
    }
}
