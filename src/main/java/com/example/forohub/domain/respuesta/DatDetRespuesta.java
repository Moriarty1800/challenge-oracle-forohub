package com.example.forohub.domain.respuesta;

import java.time.LocalDateTime;

public record DatDetRespuesta(
        Long id,
        String mensaje,
        Long idTopico,
        Long idAutor,
        LocalDateTime fechaCreacion,
        Boolean solucion
) {
    public DatDetRespuesta(Respuesta respuesta) {
        this(respuesta.getId(), respuesta.getMensaje(),
                respuesta.getTopico().getId(),
                respuesta.getAutor().getId(),
                respuesta.getFechaCreacion(),
                respuesta.getSolucion());
    }
}