package com.project.forKnowledegeTesting.service;

import com.project.forKnowledegeTesting.dto.RegisterDTO;
import com.project.forKnowledegeTesting.dto.RequestDTO;
import com.project.forKnowledegeTesting.dto.ResponseDTO;
import com.project.forKnowledegeTesting.mapper.UserMapper;
import com.project.forKnowledegeTesting.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserService userService;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    private final JwtService jwtService;
    private final UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;


    public AuthService(UserService userService, UserMapper userMapper, JwtService jwtService, UserRepository userRepository) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public ResponseDTO login(RequestDTO requestDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDTO.getUsername(),requestDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtService.generateToken(requestDTO.getUsername());
            return new ResponseDTO(token);
        }
        return new ResponseDTO("Invalid username or password");
    }

    public ResponseDTO register(RegisterDTO registerDTO) {
        registerDTO.setPassword(encoder.encode(registerDTO.getPassword()));
        userRepository.save(userMapper.toUser(registerDTO));
        String token = jwtService.generateToken(registerDTO.getUsername());
        return new ResponseDTO(token);
    }
}
