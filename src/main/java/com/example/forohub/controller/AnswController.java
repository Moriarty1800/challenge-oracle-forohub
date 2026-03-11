package com.example.forohub.controller;


import com.example.forohub.domain.respuesta.*;
import com.example.forohub.domain.topico.TopicoRep;
import com.example.forohub.domain.usuario.UsuarioRep;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("/respuestas")
public class AnswController {

    @Autowired
    private ResRep resRep;

    @Autowired
    private TopicoRep topicoRep;

    @Autowired
    private UsuarioRep usuarioRep;

    @PostMapping
    @Transactional
    public ResponseEntity registrar(@RequestBody @Valid DatRegRespuesta datos) {

        var topico = topicoRep.findById(datos.idTopico())
                .orElseThrow(() -> new RuntimeException("Topic sin resultados."));

        var autor = usuarioRep.findById(datos.idAutor())
                .orElseThrow(() -> new RuntimeException("Usuario sin resultados."));

        var respuesta = new Respuesta(datos.mensaje(), topico, autor);
        resRep.save(respuesta);

        return ResponseEntity.ok(new DatDetRespuesta(respuesta));
    }

    @GetMapping
    public ResponseEntity<Page<DatDetRespuesta>> listar(@PageableDefault(size = 10) Pageable paginacion) {
        return ResponseEntity.ok(resRep.findAll(paginacion).map(DatDetRespuesta::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity detallar(@PathVariable Long id) {
        var respuesta = resRep.getReferenceById(id);
        return ResponseEntity.ok(new DatDetRespuesta(respuesta));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity actualizar(@PathVariable Long id, @RequestBody @Valid DatActRespuesta datos) {
        var respuesta = resRep.getReferenceById(id);
        respuesta.actualizarMensaje(datos.mensaje());
        return ResponseEntity.ok(new DatDetRespuesta(respuesta));
    }

    @PutMapping("/{id}/solucion")
    @Transactional
    public ResponseEntity marcarComoSolucion(@PathVariable Long id, Principal principal) {
        var respuesta = resRep.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        var topico = respuesta.getTopico();

        String loginAutorTopico = topico.getAutor().getLogin();
        String usuarioLogueado = principal.getName();

        if (!loginAutorTopico.equals(usuarioLogueado)) {
            throw new ValidationException("Uicamente el autor del topic puede realizar una respuesta.");
        }

        respuesta.topicoSolucionado();
        topico.marcarComoSolucionado();

        return ResponseEntity.ok(new DatDetRespuesta(respuesta));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminar(@PathVariable Long id) {
        var respuesta = resRep.getReferenceById(id);
        resRep.delete(respuesta);
        return ResponseEntity.noContent().build();
    }
}