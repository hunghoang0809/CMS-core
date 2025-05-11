package com.example.BaseCMS.module.auth.service;

import com.example.BaseCMS.exc.WrongCredentialsException;
import com.example.BaseCMS.module.auth.dto.AuthenticationResponse;
import com.example.BaseCMS.module.auth.dto.AuthenticationRequest;
import com.example.BaseCMS.module.user.dto.UserResponse;
import com.example.BaseCMS.module.user.model.User;
import com.example.BaseCMS.module.user.service.CustomUserDetails;
import com.example.BaseCMS.module.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserService userDetailsService;
    private final JwtService jwtService;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword())
            );

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String jwtToken = jwtService.generateToken(userDetails);

            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (AuthenticationException ex) {
            throw new WrongCredentialsException("Wrong username or password");
        }
    }

    public UserResponse getMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getId();
        return userDetailsService.getById(userId);
    }
}