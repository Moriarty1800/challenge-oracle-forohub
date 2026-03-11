package com.example.forohub.controller;

import com.example.forohub.domain.perfil.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;

@RestController
@RequestMapping("/perfiles")
public class PerfController {

    @Autowired
    private PerfilRepo repository;

    @PostMapping
    @Transactional
    public ResponseEntity registrar(@RequestBody @Valid DatRegPerfil datos, UriComponentsBuilder uriBuilder) {
        var perfil = new Perfil(null, datos.nombre());
        repository.save(perfil);

        var uri = uriBuilder.path("/perfiles/{id}").buildAndExpand(perfil.getId()).toUri();
        return ResponseEntity.ok(new DatRegPerfil(perfil.getNombre()));
    }

    @GetMapping
    public ResponseEntity<List<Perfil>> listar() {
        return ResponseEntity.ok(repository.findAll());
    }
}