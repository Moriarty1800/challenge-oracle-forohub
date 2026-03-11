package com.example.forohub.domain.curso;

public record DatDetCurso(Long id, String nombre, Categoria categoria, Boolean activo) {
    public DatDetCurso(Curso curso) {
        this(curso.getId(), curso.getNombre(), curso.getCategoria(), curso.getActivo());
    }
}