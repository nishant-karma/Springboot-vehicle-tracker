package com.vehicleTracker.vehicleTracker.controller;

import com.vehicleTracker.vehicleTracker.DTO.LoginRequestDTO;
import com.vehicleTracker.vehicleTracker.DTO.LoginResponseDTO;
import com.vehicleTracker.vehicleTracker.DTO.UserRequestDTO;
import com.vehicleTracker.vehicleTracker.DTO.UserResponseDTO;
import com.vehicleTracker.vehicleTracker.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDTO> sigunUp(@RequestBody  UserRequestDTO req){
        System.out.println(req.toString());
        return ResponseEntity.ok(userService.signUp(req));
    }

    @PostMapping("/login")
        public ResponseEntity<LoginResponseDTO> login(@RequestBody  LoginRequestDTO req){
        return ResponseEntity.ok(userService.authenticate(req));
    }
}
