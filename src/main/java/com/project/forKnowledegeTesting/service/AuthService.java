package com.project.forKnowledegeTesting.service;

import com.project.forKnowledegeTesting.dto.RegisterDTO;
import com.project.forKnowledegeTesting.dto.RequestDTO;
import com.project.forKnowledegeTesting.dto.ResponseDTO;
import com.project.forKnowledegeTesting.exception.AuthenticationFailedException;
import com.project.forKnowledegeTesting.exception.TokenExpiredException;
import com.project.forKnowledegeTesting.mapper.UserMapper;
import com.project.forKnowledegeTesting.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.directory.AttributeInUseException;

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
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestDTO.getUsername(),requestDTO.getPassword())
            );
            if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtService.generateAccessToken(requestDTO.getUsername());
            String refreshToken = jwtService.generateRefreshToken(requestDTO.getUsername());
            return new ResponseDTO(token,refreshToken);
            }
        }catch (AuthenticationException e){
            throw new AuthenticationFailedException("Invalid username or password");
        }
        throw new AuthenticationFailedException("Invalid username or password");
    }



    public String register(RegisterDTO registerDTO) {

        if (userRepository.findByUsername(registerDTO.getUsername()).isPresent()) {
            return "Username already exists";
        }
        registerDTO.setPassword(encoder.encode(registerDTO.getPassword()));
        userRepository.save(userMapper.toUser(registerDTO));
        return "Registration successful, please log in.";
    }


    public ResponseDTO refreshToken(String refreshToken)  {
        if (refreshToken == null || refreshToken.trim().isEmpty()) {
            throw new IllegalArgumentException("Refresh token must not be null or empty");
        }
        try {

            String username = jwtService.extractUserName(refreshToken);
            if (jwtService.isTokenExpired(refreshToken)) {
              throw new TokenExpiredException("Token has expired");
            }

            String newAccessToken = jwtService.generateAccessToken(username);
            String newRefreshToken = jwtService.generateRefreshToken(username);
            return new ResponseDTO(newAccessToken, newRefreshToken);

        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException("Token expired");
        }catch (SecurityException e){
        throw new TokenExpiredException("Invalid token");
        }
    }

}
