package com.tecsup.clinica.controller;

import com.tecsup.clinica.dto.PacienteDTO;
import com.tecsup.clinica.model.Paciente;
import com.tecsup.clinica.services.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService service;

    @GetMapping
    public ResponseEntity<List<Paciente>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody PacienteDTO dto) {
        Paciente p = new Paciente();
        p.setDni(dto.getDni());
        p.setNombres(dto.getNombres());
        p.setApellidos(dto.getApellidos());
        p.setFechaNacimiento(dto.getFechaNacimiento());
        p.setSexo(dto.getSexo());
        p.setTelefono(dto.getTelefono());
        p.setDireccion(dto.getDireccion());
        p.setCorreo(dto.getCorreo());
        p.setEstado(dto.getEstado());
        return ResponseEntity.status(201).body(service.guardar(p));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody PacienteDTO dto) {
        Paciente p = service.obtenerPorId(id);
        p.setDni(dto.getDni());
        p.setNombres(dto.getNombres());
        p.setApellidos(dto.getApellidos());
        p.setFechaNacimiento(dto.getFechaNacimiento());
        p.setSexo(dto.getSexo());
        p.setTelefono(dto.getTelefono());
        p.setDireccion(dto.getDireccion());
        p.setCorreo(dto.getCorreo());
        p.setEstado(dto.getEstado());
        return ResponseEntity.ok(service.guardar(p));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok("Paciente eliminado correctamente");
    }
}
