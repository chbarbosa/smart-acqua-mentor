package com.smartacqua;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AquaristService {

    private final AquaristRepository repository;

    public AquaristService(AquaristRepository repository) {
        this.repository = repository;
    }

    public Aquarist register(String name, String email, String phone) {
        String code = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Aquarist aquarist = new Aquarist();
        aquarist.setName(name);
        aquarist.setEmail(email);
        aquarist.setPhone(phone);
        aquarist.setCode(code);
        return repository.save(aquarist);
    }

    public Optional<Aquarist> findByCode(String code) {
        return repository.findByCode(code);
    }
}

