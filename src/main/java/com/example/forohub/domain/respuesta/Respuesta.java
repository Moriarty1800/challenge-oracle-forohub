package com.example.forohub.domain.respuesta;


import com.example.forohub.domain.topico.Topico;
import com.example.forohub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "respuestas")
@Entity(name = "Respuesta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensaje;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_id")
    private Topico topico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    private LocalDateTime fechaCreacion = LocalDateTime.now();
    private Boolean activo = true;
    private Boolean solucion = false;

    public Respuesta(String mensaje, Topico topico, Usuario autor) {
        this.mensaje = mensaje;
        this.topico = topico;
        this.autor = autor;
        this.fechaCreacion = LocalDateTime.now();
        this.activo = true;
        this.solucion = false;
    }

    public void actualizarMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void topicoSolucionado() {
        this.solucion = true;
    }

    public void eliminar() {
        this.activo = false;
    }
}