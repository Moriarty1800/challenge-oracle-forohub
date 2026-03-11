package com.example.forohub.domain.curso;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "cursos")
@Entity(name = "Curso")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    private Boolean activo;

    public Curso(DatRegCurso datos) {
        this.activo = true;
        this.nombre = datos.nombre();
        this.categoria = datos.categoria();
    }

    public void actualizarDatos(DatActCurso datos) {
        if (datos.nombre() != null) {
            this.nombre = datos.nombre();
        }
        if (datos.categoria() != null) {
            this.categoria = datos.categoria();
        }
    }

    public void eliminar() {
        this.activo = false;
    }
}