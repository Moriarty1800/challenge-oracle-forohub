package com.example.forohub.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRep extends JpaRepository<Usuario, Long> {

    UserDetails findByLogin(String login);

    Usuario findByNombre(String nombre);
}