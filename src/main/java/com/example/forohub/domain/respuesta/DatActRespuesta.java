package com.example.forohub.domain.respuesta;

import jakarta.validation.constraints.NotBlank;

public record DatActRespuesta(@NotBlank String mensaje) {

}