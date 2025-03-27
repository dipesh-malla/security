package com.project.forKnowledegeTesting.controller;

import com.project.forKnowledegeTesting.dto.RegisterDTO;
import com.project.forKnowledegeTesting.dto.RequestDTO;
import com.project.forKnowledegeTesting.dto.ResponseDTO;
import com.project.forKnowledegeTesting.dto.UserDTO;
import com.project.forKnowledegeTesting.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    private final AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/api/auth/login")
    public ResponseDTO login(@RequestBody RequestDTO requestDTO) {
        return authService.login(requestDTO);
    }

    @PostMapping("/api/auth/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody RegisterDTO registerDTO) {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(registerDTO.getUsername(), registerDTO.getPassword());

        return ResponseEntity.ok(authService.register(registerDTO));
    }

    @GetMapping("/api/auth/test")
    public ResponseEntity<String> test() {
        System.out.println("Test endpint hit");
        return ResponseEntity.ok().body("test");
    }

    @GetMapping("/api/auth/hello")
    public ResponseEntity<String> hello() {
        System.out.println("test endpint hit");
        return ResponseEntity.ok().body("hello");
    }

}
