package com.tecsup.clinica.services;

import com.tecsup.clinica.exception.ResourceNotFoundException;
import com.tecsup.clinica.model.Especialidad;
import com.tecsup.clinica.repository.EspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EspecialidadService {

    @Autowired
    private EspecialidadRepository repo;

    public List<Especialidad> listar() { return repo.findAll(); }
    public Especialidad guardar(Especialidad e) { return repo.save(e); }
    public Especialidad obtener(Long id) {
        return repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Especialidad no encontrada con id: " + id));
    }
    public void eliminar(Long id) {
        repo.delete(obtener(id));
    }
}
