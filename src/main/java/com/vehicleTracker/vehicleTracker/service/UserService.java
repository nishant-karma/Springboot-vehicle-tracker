package com.vehicleTracker.vehicleTracker.service;

import com.vehicleTracker.vehicleTracker.DTO.LoginRequestDTO;
import com.vehicleTracker.vehicleTracker.DTO.LoginResponseDTO;
import com.vehicleTracker.vehicleTracker.DTO.UserRequestDTO;
import com.vehicleTracker.vehicleTracker.DTO.UserResponseDTO;
import com.vehicleTracker.vehicleTracker.Repository.UserRepository;
import com.vehicleTracker.vehicleTracker.model.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;



    public UserService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public UserResponseDTO signUp(UserRequestDTO req)
    {
        User user = new User();
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole(req.getRole());
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        return toDto(user);


    }

    public LoginResponseDTO authenticate(LoginRequestDTO input) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));
        User user = (User) authentication.getPrincipal();
        String jwtToken = jwtService.generateToken(user);
        LoginResponseDTO loginResponse = new LoginResponseDTO().setToken(jwtToken);
        System.out.println("Login Successfull" + input.getEmail());
        return loginResponse;
    }



    public UserResponseDTO toDto(User user){
        UserResponseDTO dto = new UserResponseDTO();
        dto.setUserId(user.getUserId());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());

        return dto;
    }

}
