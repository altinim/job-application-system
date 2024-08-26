package com.example.careerify.common.security;

import com.example.careerify.common.dto.JwtAuthenticationResponseDTO;
import com.example.careerify.common.dto.SignInRequestDTO;
import com.example.careerify.common.dto.SignUpRequestDTO;
import com.example.careerify.common.enums.Role;
import com.example.careerify.common.jwt.JwtService;
import com.example.careerify.model.Applicant;
import com.example.careerify.repository.ApplicantRepository;
import com.example.careerify.service.ApplicantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {

    private final ApplicantRepository userRepository;
    private final ApplicantService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponseDTO signUp(SignUpRequestDTO request) {
        var user = Applicant.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .build();

        user = userService.save(user);

        UserDetails userDetails = userService.userDetailsService().loadUserByUsername(user.getEmail());

        var jwt = jwtService.generateToken(userDetails, user.getId());
        var refreshToken = jwtService.generateRefreshToken(user.getId());  // Generate refresh token

        return JwtAuthenticationResponseDTO.builder()
                .token(jwt)
                .refreshToken(refreshToken)  // Return refresh token
                .build();
    }

    public JwtAuthenticationResponseDTO signIn(SignInRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));

        UserDetails userDetails = userService.userDetailsService().loadUserByUsername(user.getEmail());

        var jwt = jwtService.generateToken(userDetails, user.getId());
        var refreshToken = jwtService.generateRefreshToken(user.getId());  // Generate refresh token

        UUID userId = jwtService.extractUserId(jwt);

        // Log the userId to verify the extraction
        log.info("Current logged-in user ID: {}", userId);

        return JwtAuthenticationResponseDTO.builder()
                .token(jwt)
                .refreshToken(refreshToken)  // Return refresh token
                .build();
    }
}
