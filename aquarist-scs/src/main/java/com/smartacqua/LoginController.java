package com.smartacqua;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LoginController {

    private final AquaristRepository repository;
    private final PasswordEncoder encoder;

    public LoginController(AquaristRepository repository) {
        this.repository = repository;
        this.encoder = new BCryptPasswordEncoder();
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String password = payload.get("password");

        Optional<Aquarist> opt = repository.findByEmail(email);
        if (opt.isPresent() && encoder.matches(password, opt.get().getPassword())) {
            return ResponseEntity.ok(Map.of("code", opt.get().getCode()));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
