package com.tecsup.service;

import com.tecsup.model.Empleado;
import com.tecsup.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository repo;

    public List<Empleado> listar() {
        return repo.findAll();
    }

    public Empleado obtener(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Transactional
    public Empleado guardar(Empleado empleado) {
        return repo.save(empleado);
    }

    @Transactional
    public Empleado actualizar(Long id, Empleado nuevo) {
        Empleado existente = obtener(id);
        if (existente == null) {
            return null;
        }
        existente.setNombres(nuevo.getNombres());
        existente.setApellidos(nuevo.getApellidos());
        existente.setEmail(nuevo.getEmail());
        existente.setSueldo(nuevo.getSueldo());
        return repo.save(existente);
    }

    @Transactional
    public boolean eliminar(Long id) {
        if (!repo.existsById(id)) {
            return false;
        }
        repo.deleteById(id);
        return true;
    }
}
