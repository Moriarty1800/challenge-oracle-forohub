package com.example.forohub.controller;

import com.example.forohub.domain.curso.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/cursos")
public class CurController {

    @Autowired
    private CursoRepo repository;

    @PostMapping
    @Transactional
    public ResponseEntity registrar(@RequestBody @Valid DatRegCurso datos, UriComponentsBuilder uriBuilder) {
        var curso = new Curso(null, datos.nombre(), datos.categoria(), true);
        repository.save(curso);

        var uri = uriBuilder.path("/cursos/{id}").buildAndExpand(curso.getId()).toUri();
        return ResponseEntity.created(uri).body(new DatDetCurso(curso));
    }

    @GetMapping
    public ResponseEntity<Page<DatDetCurso>> listar(@PageableDefault(size = 10) Pageable paginacion) {
        return ResponseEntity.ok(repository.findAll(paginacion).map(DatDetCurso::new));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminar(@PathVariable Long id) {
        var curso = repository.getReferenceById(id);
        curso.eliminar(); // Borrado lógico
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Transactional