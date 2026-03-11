package com.example.forohub.domain.curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatRegCurso(
        @NotBlank
        String nombre,

        @NotNull
        Categoria categoria
) {
}