package com.tecsup.controller;

import com.tecsup.dto.ClienteDTO;
import com.tecsup.model.Cliente;
import com.tecsup.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping
    public ResponseEntity<List<Cliente>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PostMapping
    public ResponseEntity<Cliente> registrar(@Valid @RequestBody ClienteDTO dto) {
        Cliente c = new Cliente();
        c.setNombre(dto.getNombre());
        c.setApellidos(dto.getApellidos());
        c.setDireccion(dto.getDireccion());
        c.setTelefono(dto.getTelefono());
        c.setNroDni(dto.getNroDni());
        c.setEstado(dto.getEstado());
        return ResponseEntity.status(201).body(service.guardar(c));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizar(@PathVariable Long id, @Valid @RequestBody ClienteDTO dto) {
        Cliente c = service.obtener(id);
        c.setNombre(dto.getNombre());
        c.setApellidos(dto.getApellidos());
        c.setDireccion(dto.getDireccion());
        c.setTelefono(dto.getTelefono());
        c.setNroDni(dto.getNroDni());
        c.setEstado(dto.getEstado());
        return ResponseEntity.ok(service.guardar(c));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok("Cliente eliminado correctamente");
    }
}
