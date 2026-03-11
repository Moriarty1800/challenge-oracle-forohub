package com.example.forohub.domain.perfil;

import jakarta.validation.constraints.NotBlank;

public record DatRegPerfil(@NotBlank String nombre) {
}