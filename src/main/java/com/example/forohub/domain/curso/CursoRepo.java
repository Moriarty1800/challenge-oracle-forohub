package com.example.forohub.domain.curso;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepo extends JpaRepository<Curso, Long> {

    Curso findByNombre(String nombre);
}