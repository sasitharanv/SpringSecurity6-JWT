package com.springsecurity.demo.controller;

import com.springsecurity.demo.Dto.LoginDto;
import com.springsecurity.demo.Jwt.JWTAuthResponse;
import com.springsecurity.demo.Service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class AuthController {

    private AuthService authService;

    // Build Login REST API
    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> authenticate(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }
}