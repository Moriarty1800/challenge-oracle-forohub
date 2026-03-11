package com.example.forohub.controller;

import com.example.forohub.domain.usuario.Usuario;
import com.example.forohub.domain.usuario.DatAutUsuario;
import com.example.forohub.infra.security.DatosJWTToken;
import com.example.forohub.infra.security.TokenS;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenS tokenS;

    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatAutUsuario datos) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(datos.login(), datos.clave());
        var usuarioAutenticado = authenticationManager.authenticate(authToken);

        var JWTtoken = tokenS.generarToken((Usuario) usuarioAutenticado.getPrincipal());

        return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
    }
}