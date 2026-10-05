package com.example.demo.controller;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> crearTokenAutenticacion(@Valid @RequestBody AuthRequest authRequest) {
        try {
            // Spring Sec. internamente encripta la contraseña enviada y la compara con
            // la de la BD
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Credenciales incorrectas. Verifique usuario y contraseña.");
        }

        // Si pasa la autenticación, buscamos al usuario para generar su token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        // devolvemos el token en un json limpio
        return ResponseEntity.ok(new AuthResponse(jwt));
    }
}