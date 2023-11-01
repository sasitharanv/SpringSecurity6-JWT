package com.springsecurity.demo.controller;
import com.springsecurity.demo.Dto.UserDto;
import com.springsecurity.demo.Service.UserService;
import com.springsecurity.demo.modal.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
        try {
            User registeredUser = userService.registerUser(userDto);
            return new ResponseEntity<>("User created with ID: " + registeredUser.getId(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("User registration failed: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
        try {
            UserDto userDto = userService.getUserById(userId);

            if (userDto != null) {
                return new ResponseEntity<>(userDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
