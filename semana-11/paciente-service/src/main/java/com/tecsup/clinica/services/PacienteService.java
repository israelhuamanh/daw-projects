package com.tecsup.clinica.services;

import com.tecsup.clinica.exception.ResourceNotFoundException;
import com.tecsup.clinica.model.Paciente;
import com.tecsup.clinica.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository repo;

    public List<Paciente> listar() {
        return repo.findAll();
    }

    public Paciente guardar(Paciente p) {
        return repo.save(p);
    }

    public Paciente obtenerPorId(Long id) {
        return repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Paciente no encontrado con id: " + id));
    }

    public void eliminar(Long id) {
        Paciente p = obtenerPorId(id);
        repo.delete(p);
    }
}
