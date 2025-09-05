package com.smartacqua;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AquaristService {

    private final AquaristRepository repository;
    private final PasswordEncoder encoder;

    public AquaristService(AquaristRepository repository) {
        this.repository = repository;
        this.encoder = new BCryptPasswordEncoder();
    }

    public Aquarist register(String name, String email, String phone, String rawPassword) {
        String code = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Aquarist aquarist = new Aquarist();
        aquarist.setName(name);
        aquarist.setEmail(email);
        aquarist.setPhone(phone);
        aquarist.setPassword(encoder.encode(rawPassword));
        aquarist.setCode(code);
        return repository.save(aquarist);
    }

    public boolean validateLogin(String email, String rawPassword) {
        Optional<Aquarist> opt = repository.findByEmail(email);
        return opt.isPresent() && encoder.matches(rawPassword, opt.get().getPassword());
    }

    public Optional<Aquarist> findByCode(String code) {
        return repository.findByCode(code);
    }public void updateAquarist(String code, String name, String email, String phone, String rawPassword) {
        Optional<Aquarist> opt = repository.findByCode(code);
        if (opt.isPresent()) {
            Aquarist a = opt.get();
            a.setName(name);
            a.setEmail(email);
            a.setPhone(phone);
            if (rawPassword != null && !rawPassword.isBlank()) {
                a.setPassword(encoder.encode(rawPassword));
            }
            repository.save(a);
        }
    }

}

