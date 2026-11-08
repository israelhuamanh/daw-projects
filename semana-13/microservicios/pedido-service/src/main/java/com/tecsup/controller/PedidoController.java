package com.tecsup.controller;

import com.tecsup.dto.PedidoDTO;
import com.tecsup.model.Pedido;
import com.tecsup.services.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @GetMapping
    public ResponseEntity<List<Pedido>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PostMapping
    public ResponseEntity<Pedido> guardar(@Valid @RequestBody PedidoDTO dto) {
        return ResponseEntity.status(201).body(service.guardar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Pedido> actualizarEstado(@PathVariable Long id, @RequestParam String nuevoEstado) {
        return ResponseEntity.ok(service.actualizarEstado(id, nuevoEstado));
    }

    @PutMapping("/{id}/anular")
    public ResponseEntity<Pedido> anular(@PathVariable Long id) {
        return ResponseEntity.ok(service.anular(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Pedido> eliminarLogico(@PathVariable Long id) {
        // En lugar de eliminar físicamente, anula el pedido según requerimiento
        return ResponseEntity.ok(service.anular(id));
    }
}
