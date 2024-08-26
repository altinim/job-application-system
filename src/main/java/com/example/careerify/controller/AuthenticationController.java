package com.example.careerify.controller;


import com.example.careerify.common.dto.JwtAuthenticationResponseDTO;
import com.example.careerify.common.dto.SignInRequestDTO;
import com.example.careerify.common.dto.SignUpRequestDTO;
import com.example.careerify.common.security.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public JwtAuthenticationResponseDTO signUp(@RequestBody SignUpRequestDTO request) {
        return authenticationService.signUp(request);
    }

    @PostMapping("/sign-in")
    public JwtAuthenticationResponseDTO signIn(@RequestBody SignInRequestDTO request) {
        return authenticationService.signIn(request);
    }
}