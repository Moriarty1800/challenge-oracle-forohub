package com.example.forohub.domain.usuario;

public record DatLisUsuario(Long id, String nombre, String email) {
    public DatLisUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getNombre(), usuario.getEmail());
    }
}