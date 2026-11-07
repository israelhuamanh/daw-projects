package com.tecsup.service;

import com.tecsup.models.Curso;
import com.tecsup.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepo;

    public List<Curso> listar() {
        return cursoRepo.findAll();
    }

    public Curso obtener(Long id) {
        return cursoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con ID: " + id));
    }

    public Curso guardar(Curso curso) {
        return cursoRepo.save(curso);
    }

    public void eliminar(Long id) {
        cursoRepo.deleteById(id);
    }
}
