package com.smartacqua;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class AquaristService {

    private final Map<String, AquaristDTO> aquarists = new HashMap<>();

    public AquaristDTO register(String name, String email, String phone) {
        String code = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        AquaristDTO dto = new AquaristDTO(name, email, phone, code);
        aquarists.put(code, dto);
        return dto;
    }

    public Optional<AquaristDTO> findByCode(String code) {
        return Optional.ofNullable(aquarists.get(code));
    }
}

