package com.tecsup.clinica.controller;

import com.tecsup.clinica.dto.AtencionDTO;
import com.tecsup.clinica.dto.CitaDTO;
import com.tecsup.clinica.model.Atencion;
import com.tecsup.clinica.model.Cita;
import com.tecsup.clinica.services.CitaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    @Autowired
    private CitaService service;

    @GetMapping
    public ResponseEntity<List<Cita>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PostMapping
    public ResponseEntity<Cita> registrar(@Valid @RequestBody CitaDTO dto) {
        Cita c = new Cita();
        c.setFecha(dto.getFecha());
        c.setHora(dto.getHora());
        c.setIdPaciente(dto.getIdPaciente());
        c.setIdMedico(dto.getIdMedico());
        c.setMotivo(dto.getMotivo());
        return ResponseEntity.status(201).body(service.registrar(c));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cita> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Cita> actualizarEstado(@PathVariable Long id, @RequestParam String nuevoEstado) {
        return ResponseEntity.ok(service.actualizarEstado(id, nuevoEstado));
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Cita> cancelarCita(@PathVariable Long id) {
        return ResponseEntity.ok(service.cancelar(id));
    }

    @PostMapping("/{id}/atencion")
    public ResponseEntity<Atencion> registrarAtencion(@PathVariable Long id, @Valid @RequestBody AtencionDTO dto) {
        Atencion a = new Atencion();
        a.setDiagnostico(dto.getDiagnostico());
        a.setTratamiento(dto.getTratamiento());
        a.setObservaciones(dto.getObservaciones());
        return ResponseEntity.status(201).body(service.registrarAtencion(id, a));
    }

    @GetMapping("/{id}/atencion")
    public ResponseEntity<Atencion> obtenerAtencion(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerAtencionPorCita(id));
    }
}
