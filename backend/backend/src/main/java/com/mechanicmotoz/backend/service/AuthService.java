package com.mechanicmotoz.backend.service;

import com.mechanicmotoz.backend.dto.AuthRequest;
import com.mechanicmotoz.backend.dto.AuthResponse;
import com.mechanicmotoz.backend.entity.User;
import com.mechanicmotoz.backend.repository.UserRepository;
import com.mechanicmotoz.backend.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    
    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtTokenProvider.generateToken(userDetails);
        String refreshToken = jwtTokenProvider.generateRefreshToken(userDetails);
        
        // Actualizar último login
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
        
        return new AuthResponse(
            token,
            refreshToken,
            userDetails.getUsername(),
            userDetails.getAuthorities().iterator().next().getAuthority(),
            jwtTokenProvider.extractClaim(token, claims -> claims.getExpiration().getTime())
        );
    }
    
    public AuthResponse refresh(String refreshToken) {
        // TODO: Implementar refresh logic
        return null;
    }
    
    public void logout(String token) {
        // TODO: Implementar logout logic (blacklist)
    }
}