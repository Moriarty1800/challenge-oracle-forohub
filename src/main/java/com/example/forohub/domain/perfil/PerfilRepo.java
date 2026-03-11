package com.example.forohub.domain.perfil;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepo extends JpaRepository<Perfil, Long> {
    Perfil findByNombre(String nombre);
}