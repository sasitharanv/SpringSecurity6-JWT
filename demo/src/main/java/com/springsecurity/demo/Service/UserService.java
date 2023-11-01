package com.springsecurity.demo.Service;
import com.springsecurity.demo.Dto.UserDto;
import com.springsecurity.demo.modal.User;
import com.springsecurity.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public UserDto getUserById(Long userId) {
        // Use the userRepository to fetch the user from the database by ID
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            // Map the User entity to a UserDto (you may need a mapper for this)
            User user = userOptional.get();
            UserDto userDto = mapUserToDto(user);

            return userDto;
        } else {
            // User not found, return null or handle appropriately
            return null;
        }
    }

    private UserDto mapUserToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getUsername());
        // Set other user properties as needed
        return userDto;
    }

    public User registerUser(UserDto userDto) {
        User newUser = new User();
        newUser.setName(userDto.getName());
        newUser.setUsername(userDto.getName());
        newUser.setEmail(userDto.getEmail());

        // Encode the user's password before storing it in the database
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        newUser.setPassword(encodedPassword);

        // You can set other user properties as needed

        // Save the user to the database
        return userRepository.save(newUser);
    }
}

