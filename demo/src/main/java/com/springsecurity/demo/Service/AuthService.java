package com.springsecurity.demo.Service;

import com.springsecurity.demo.Dto.LoginDto;


public interface AuthService {
    String login(LoginDto loginDto);
}