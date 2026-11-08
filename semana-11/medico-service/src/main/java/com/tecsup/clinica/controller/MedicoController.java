package com.tecsup.clinica.controller;

import com.tecsup.clinica.dto.HorarioMedicoDTO;
import com.tecsup.clinica.dto.MedicoDTO;
import com.tecsup.clinica.model.Especialidad;
import com.tecsup.clinica.model.HorarioMedico;
import com.tecsup.clinica.model.Medico;
import com.tecsup.clinica.services.EspecialidadService;
import com.tecsup.clinica.services.MedicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicos")
public class MedicoController {

    @Autowired
    private MedicoService service;

    @Autowired
    private EspecialidadService especialidadService;

    @GetMapping
    public ResponseEntity<List<Medico>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody MedicoDTO dto) {
        Especialidad esp = especialidadService.obtener(dto.getIdEspecialidad());
        Medico m = new Medico();
        m.setCmp(dto.getCmp());
        m.setNombres(dto.getNombres());
        m.setApellidos(dto.getApellidos());
        m.setTelefono(dto.getTelefono());
        m.setCorreo(dto.getCorreo());
        m.setEspecialidad(esp);
        m.setEstado(dto.getEstado());
        return ResponseEntity.status(201).body(service.guardar(m));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody MedicoDTO dto) {
        Medico m = service.obtenerPorId(id);
        Especialidad esp = especialidadService.obtener(dto.getIdEspecialidad());
        m.setCmp(dto.getCmp());
        m.setNombres(dto.getNombres());
        m.setApellidos(dto.getApellidos());
        m.setTelefono(dto.getTelefono());
        m.setCorreo(dto.getCorreo());
        m.setEspecialidad(esp);
        m.setEstado(dto.getEstado());
        return ResponseEntity.ok(service.guardar(m));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok("Médico eliminado correctamente");
    }

    @PostMapping("/{id}/horarios")
    public ResponseEntity<?> agregarHorario(@PathVariable Long id, @Valid @RequestBody HorarioMedicoDTO dto) {
        HorarioMedico h = new HorarioMedico();
        h.setDiaSemana(dto.getDiaSemana());
        h.setHoraInicio(dto.getHoraInicio());
        h.setHoraFin(dto.getHoraFin());
        return ResponseEntity.status(201).body(service.agregarHorario(id, h));
    }

    @GetMapping("/{id}/horarios")
    public ResponseEntity<List<HorarioMedico>> listarHorarios(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarHorariosPorMedico(id));
    }
}
