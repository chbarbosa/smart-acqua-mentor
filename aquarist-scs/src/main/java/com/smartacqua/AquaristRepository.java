package com.smartacqua;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AquaristRepository extends JpaRepository<Aquarist, Long> {
    Optional<Aquarist> findByEmail(String email);
    Optional<Aquarist> findByCode(String code);
}
