package com.example.forohub.domain.usuario;

public record DatDetUsuario(Long id, String login, String nombre, String email) {
    public DatDetUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getLogin(), usuario.getNombre(), usuario.getEmail());
    }
}