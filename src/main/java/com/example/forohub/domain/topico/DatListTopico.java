package com.example.forohub.domain.topico;


import java.time.LocalDateTime;

public record DatListTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        StaTopico status,
        String autor,
        String curso
) {
    public DatListTopico(Topico topico) {
        this(topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus(),
                topico.getAutor().getNombre(),
                topico.getCurso().getNombre());
    }
}