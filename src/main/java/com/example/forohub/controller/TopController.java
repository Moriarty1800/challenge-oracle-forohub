package com.example.forohub.controller;


import com.example.forohub.domain.topico.Topico;
import com.example.forohub.domain.topico.TopicoRep;
import com.example.forohub.domain.topico.DatActTopico;
import com.example.forohub.domain.topico.DatDetTopico;
import com.example.forohub.domain.topico.DatRegTopico;
import com.example.forohub.domain.usuario.UsuarioRep;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import com.example.forohub.domain.topico.DatListTopico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
import com.example.forohub.domain.curso.CursoRepo;

@RestController
@RequestMapping("/topicos")
public class TopController {

    private final TopicoRep repository;
    private final UsuarioRep usuarioRep;
    private final CursoRepo cursoRepo;

    public TopController(TopicoRep repository, UsuarioRep usuarioRep, CursoRepo cursoRepo) {
        this.repository = repository;
        this.usuarioRep = usuarioRep;
        this.cursoRepo = cursoRepo;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> registrarTopico(@RequestBody @Valid DatRegTopico datos, UriComponentsBuilder uriBuilder) {

        if (repository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())) {
            return ResponseEntity.badRequest().body("Error: Ya hay un topic con el mismo título y mensaje.");
        }

        var autor = usuarioRep.findByNombre(datos.autor());
        var curso = cursoRepo.findByNombre(datos.curso());

        var topico = new Topico(datos, autor, curso);
        repository.save(topico);

        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DatDetTopico(topico));
    }

    @GetMapping
    public ResponseEntity<Page<DatListTopico>> listarTopicos(
            @RequestParam(required = false) String curso,
            @RequestParam(required = false) Integer anio,
            @PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.ASC) Pageable paginacion) {

        Page<Topico> pagina;

        if (curso != null && anio != null) {
            pagina = repository.findByCursoYAnio(curso, anio, paginacion);
        } else {
            pagina = repository.findAllByActivoTrue(paginacion);
        }

        return ResponseEntity.ok(pagina.map(DatListTopico::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detallar(@PathVariable Long id) {
        return repository.findById(id)
                .map(topico -> ResponseEntity.ok(new DatDetTopico(topico)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody @Valid DatActTopico datos) {
        var topicoOptional = repository.findById(id);
        if (topicoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var topico = topicoOptional.get();

        if (!topico.getTitulo().equals(datos.titulo()) || !topico.getMensaje().equals(datos.mensaje())) {
            if (repository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())) {
                return ResponseEntity.badRequest().body("Error: Ya hay otro topic con el mismo título y mensaje.");
            }
        }

        topico.actualizarDatos(datos);
        return ResponseEntity.ok(new DatDetTopico(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        var topico = repository.getReferenceById(id);
        topico.eliminar();

        return ResponseEntity.noContent().build();
    }
}