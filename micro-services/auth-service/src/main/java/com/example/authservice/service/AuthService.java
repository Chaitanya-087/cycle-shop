package com.example.authservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.authservice.config.CustomUserDetails;
import com.example.authservice.entity.User;
import com.example.authservice.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public String saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "user added successfully";
    }

    public String generateToken(String username) {
        User user = userRepository.findByUsername(username).get();
        UserDetails userDetails = new CustomUserDetails(user);
        return jwtService.generateToken(userDetails);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }

}
