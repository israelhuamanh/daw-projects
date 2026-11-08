package com.tecsup.clinica.controller;

import com.tecsup.clinica.dto.EspecialidadDTO;
import com.tecsup.clinica.model.Especialidad;
import com.tecsup.clinica.services.EspecialidadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadController {

    @Autowired
    private EspecialidadService service;

    @GetMapping
    public ResponseEntity<List<Especialidad>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PostMapping
    public ResponseEntity<Especialidad> registrar(@Valid @RequestBody EspecialidadDTO dto) {
        Especialidad e = new Especialidad();
        e.setNombre(dto.getNombre());
        e.setDescripcion(dto.getDescripcion());
        e.setEstado(dto.getEstado());
        return ResponseEntity.status(201).body(service.guardar(e));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Especialidad> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtener(id));
    }
}
