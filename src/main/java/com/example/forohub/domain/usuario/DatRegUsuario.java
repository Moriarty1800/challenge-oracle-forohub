package com.example.forohub.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DatRegUsuario(
        @NotBlank String login,
        @NotBlank String clave,
        @NotBlank String nombre,
        @NotBlank @Email String email
) {}