package org.example.expert.domain.auth.service;

import org.example.expert.config.JwtUtil;
import org.example.expert.config.PasswordEncoder;
import org.example.expert.domain.auth.dto.request.SignupRequest;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.user.enums.UserRole;
import org.example.expert.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    AuthService authService;
    @Mock
    private UserRepository userRepository;
    @Spy
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtUtil jwtUtil;

    @Test
    public void 유저_이메일이없을때_IRE예외발생() {
        //given
        SignupRequest request = new SignupRequest(null, "1234", "userRole.USER");
        //when
        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> {
            authService.signup(request);
        });
        //then
        assertEquals("이메일이 존재하지않습니다.", exception.getMessage());
    }

    @Test
    public void 유저_이메일이_중복일때_IRE예외_발생() {
        //given
       SignupRequest request = new SignupRequest("@email.com","1234","user");
       given(userRepository.existsByEmail("@email.com")).willReturn(true);
        //when

        InvalidRequestException exception = assertThrows(InvalidRequestException.class , ()->{
           authService.signup(request);
        });

        //then
        assertEquals("이미 존재하는 이메일입니다." , exception.getMessage());
    }





}
