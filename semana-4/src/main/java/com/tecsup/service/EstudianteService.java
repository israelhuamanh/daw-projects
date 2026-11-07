package com.tecsup.service;

import com.tecsup.models.Curso;
import com.tecsup.models.Estudiante;
import com.tecsup.repository.CursoRepository;
import com.tecsup.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepo;

    @Autowired
    private CursoRepository cursoRepo;

    public List<Estudiante> listar() {
        return estudianteRepo.findAll();
    }

    public Estudiante obtener(Long id) {
        return estudianteRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estudiante no encontrado con ID: " + id));
    }

    @Transactional
    public Estudiante crear(Estudiante estudiante) {
        // Si se enviaron cursos con ID, asociar entidades gestionadas
        if (estudiante.getCursos() != null && !estudiante.getCursos().isEmpty()) {
            Set<Curso> cursosGestionados = new HashSet<>();
            for (Curso c : estudiante.getCursos()) {
                if (c.getId() != null) {
                    Curso curso = cursoRepo.findById(c.getId())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso no encontrado con ID: " + c.getId()));
                    cursosGestionados.add(curso);
                }
            }
            estudiante.setCursos(cursosGestionados);
        }
        return estudianteRepo.save(estudiante);
    }

    @Transactional
    public Estudiante agregarCurso(Long estudianteId, Long cursoId) {
        Estudiante estudiante = obtener(estudianteId);
        Curso curso = cursoRepo.findById(cursoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso no encontrado con ID: " + cursoId));

        // Validación de negocio: no permitir inscripción duplicada
        if (estudiante.getCursos().contains(curso)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El estudiante ya se encuentra inscrito en el curso: " + curso.getNombre());
        }

        estudiante.getCursos().add(curso);
        return estudianteRepo.save(estudiante);
    }

    @Transactional
    public Estudiante quitarCurso(Long estudianteId, Long cursoId) {
        Estudiante estudiante = obtener(estudianteId);
        Curso curso = cursoRepo.findById(cursoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso no encontrado con ID: " + cursoId));

        if (!estudiante.getCursos().contains(curso)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El estudiante no está inscrito en el curso con ID: " + cursoId);
        }

        estudiante.getCursos().remove(curso);
        return estudianteRepo.save(estudiante);
    }

    @Transactional(readOnly = true)
    public Set<Curso> listarCursosPorEstudiante(Long estudianteId) {
        Estudiante estudiante = obtener(estudianteId);
        // Fuerza la carga de la colección dentro de la transacción
        estudiante.getCursos().size();
        return estudiante.getCursos();
    }

    @Transactional
    public void eliminar(Long id) {
        Estudiante estudiante = obtener(id);
        estudianteRepo.delete(estudiante);
    }
}
