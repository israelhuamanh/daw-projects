package com.tecsup.controller;

import java.util.List;

import com.tecsup.dto.ProductoDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tecsup.model.Producto;
import com.tecsup.service.ProductoService;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService service;

    // 1. GET - Listar todos los productos
    @GetMapping
    public ResponseEntity<List<Producto>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // 2. POST - Guardar nuevo producto (con DTO y validación @Valid)
    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody ProductoDTO dto) {
        Producto p = new Producto();
        p.setNombre(dto.getNombre());
        p.setPrecio(dto.getPrecio());
        p.setStock(dto.getStock());
        p.setCategoria(dto.getCategoria());

        Producto guardado = service.guardar(p);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    // 3. GET por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        Producto p = service.obtener(id);

        if (p == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado con ID: " + id);
        }

        return ResponseEntity.ok(p);
    }

    // 4. PUT - Actualizar producto existente (con DTO y validación @Valid)
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id,
                                        @Valid @RequestBody ProductoDTO dto) {
        Producto existente = service.obtener(id);

        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no existe con ID: " + id);
        }

        existente.setNombre(dto.getNombre());
        existente.setPrecio(dto.getPrecio());
        existente.setStock(dto.getStock());
        existente.setCategoria(dto.getCategoria());

        return ResponseEntity.ok(service.guardar(existente));
    }

    // 5. DELETE - Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Producto p = service.obtener(id);

        if (p == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe producto con ID: " + id);
        }

        service.eliminar(id);
        return ResponseEntity.ok("Producto eliminado correctamente con ID: " + id);
    }

    // 6. Actividad: Buscar productos por nombre (Query Param: /api/productos/buscar?nombre=Laptop)
    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> buscarPorNombre(@RequestParam(name = "nombre", required = false, defaultValue = "") String nombre) {
        List<Producto> resultados = service.buscarPorNombre(nombre);
        return ResponseEntity.ok(resultados);
    }

    // 7. Actividad Alternativa: Buscar productos por nombre en Path Variable (/api/productos/buscar/{nombre})
    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<List<Producto>> buscarPorNombrePath(@PathVariable String nombre) {
        List<Producto> resultados = service.buscarPorNombre(nombre);
        return ResponseEntity.ok(resultados);
    }

    // 8. Filtrar productos por categoría
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Producto>> listarPorCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(service.buscarPorCategoria(categoria));
    }
}
