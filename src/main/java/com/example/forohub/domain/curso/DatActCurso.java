package com.example.forohub.domain.curso;

import jakarta.validation.constraints.NotNull;

public record DatActCurso(
        @NotNull
        Long id,
        String nombre,
        Categoria categoria
) {
}