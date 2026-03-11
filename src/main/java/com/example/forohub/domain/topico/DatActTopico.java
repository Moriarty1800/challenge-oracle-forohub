package com.example.forohub.domain.topico;

import jakarta.validation.constraints.NotBlank;

public record DatActTopico(
        @NotBlank String titulo,
        @NotBlank String mensaje
) {
}