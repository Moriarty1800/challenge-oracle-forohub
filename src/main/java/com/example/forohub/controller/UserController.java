package com.example.forohub.controller;


import com.example.forohub.domain.perfil.Perfil;
import com.example.forohub.domain.perfil.PerfilRepo;
import com.example.forohub.domain.usuario.Usuario;
import com.example.forohub.domain.usuario.UsuarioRep;
import com.example.forohub.domain.usuario.DatDetUsuario;
import com.example.forohub.domain.usuario.DatLisUsuario;
import com.example.forohub.domain.usuario.DatRegUsuario;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/usuarios")
public class UserController {

    @Autowired
    private UsuarioRep repository;

    @Autowired
    private PerfilRepo perfilRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping
    @Transactional
    public ResponseEntity registrar(@RequestBody @Valid DatRegUsuario datos, UriComponentsBuilder uriBuilder) {

        var claveEncriptada = passwordEncoder.encode(datos.clave());

        var perfilDefault = perfilRepo.findByNombre("ROLE_USER");

        var perfiles = new java.util.ArrayList<Perfil>();
        if (perfilDefault != null) {
            perfiles.add(perfilDefault);
        }

        var usuario = new Usuario(datos, claveEncriptada);
        repository.save(usuario);

        var uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new DatDetUsuario(usuario));
    }

    @GetMapping
    public ResponseEntity<Page<DatLisUsuario>> listar(@PageableDefault(size = 10) Pageable paginacion) {
        return ResponseEntity.ok(repository.findAll(paginacion).map(DatLisUsuario::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity detallar(@PathVariable Long id) {
        var usuario = repository.getReferenceById(id);
        return ResponseEntity.ok(new DatDetUsuario(usuario));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminar(@PathVariable Long id) {
        var usuario = repository.getReferenceById(id);
        repository.delete(usuario);
        return ResponseEntity.noContent().build();
    }
}