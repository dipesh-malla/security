package com.project.forKnowledegeTesting.controller;

import com.project.forKnowledegeTesting.dto.RegisterDTO;
import com.project.forKnowledegeTesting.dto.RequestDTO;
import com.project.forKnowledegeTesting.dto.ResponseDTO;
import com.project.forKnowledegeTesting.service.AuthService;
import com.project.forKnowledegeTesting.exception.TokenExpiredException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.directory.AttributeInUseException;

@RestController
public class AuthController {

    private final AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<?> login(
            @RequestBody RequestDTO requestDTO,
            HttpServletResponse response
    ) {
        try{
            ResponseDTO responseDTO = authService.login(requestDTO);
            String refreshToken = responseDTO.refreshToken();
            Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
            refreshTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setMaxAge(60 * 60 * 24 * 30);
            response.addCookie(refreshTokenCookie);
            return ResponseEntity.ok(responseDTO);
        }catch (AttributeInUseException e){
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }

    @PostMapping("/api/auth/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
        try{
            return ResponseEntity.ok(authService.register(registerDTO));
        }catch (Exception e){
            return ResponseEntity.status(500).body("Registration failed, please try again");
        }
    }


    @PostMapping("/api/auth/refresh")
    public ResponseEntity<?> refresh(
            @CookieValue(name = "refreshToken", required = false) String refreshToken,
            HttpServletResponse response) {
      try{
          ResponseDTO responseDTO = authService.refreshToken(refreshToken);
          String refresh = responseDTO.refreshToken();
          Cookie refreshTokenCookie = new Cookie("refreshToken", refresh);
          refreshTokenCookie.setHttpOnly(true);
          refreshTokenCookie.setMaxAge(60 * 60 * 24 * 30);
          response.addCookie(refreshTokenCookie);
          return ResponseEntity.ok(responseDTO);
      }catch (TokenExpiredException e){
            return ResponseEntity.status(401).body("Token expired");
      }
    }


    @GetMapping("/api/auth/test")
    public ResponseEntity<String> test() {
        System.out.println("Test endpint hit");
        return ResponseEntity.ok().body("test");
    }



}
