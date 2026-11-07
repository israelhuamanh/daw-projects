package com.tecsup.controllers;

import com.tecsup.models.Curso;
import com.tecsup.models.Estudiante;
import com.tecsup.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    @Autowired
    private EstudianteService service;

    @GetMapping
    public List<Estudiante> listar() {
        return service.listar();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estudiante crear(@RequestBody Estudiante estudiante) {
        return service.crear(estudiante);
    }

    @GetMapping("/{id}")
    public Estudiante obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @PostMapping("/{estudianteId}/cursos/{cursoId}")
    public Estudiante agregarCurso(@PathVariable Long estudianteId, @PathVariable Long cursoId) {
        return service.agregarCurso(estudianteId, cursoId);
    }

    @DeleteMapping("/{estudianteId}/cursos/{cursoId}")
    public Estudiante quitarCurso(@PathVariable Long estudianteId, @PathVariable Long cursoId) {
        return service.quitarCurso(estudianteId, cursoId);
    }

    @GetMapping("/{id}/cursos")
    public Set<Curso> listarCursosPorEstudiante(@PathVariable Long id) {
        return service.listarCursosPorEstudiante(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
