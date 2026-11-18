package com.tecsup.services;

import com.tecsup.client.ProductoClient;
import com.tecsup.dto.ProductoDTO;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoCircuitService {

    @Autowired
    private ProductoClient productoClient;

    @Bulkhead(name = "productoBulkhead")
    @CircuitBreaker(name = "productoService", fallbackMethod = "productoFallback")
    public ProductoDTO obtenerProducto(Long id) {
        System.out.println("Llamando a producto-service... id=" + id);
        return productoClient.obtenerProducto(id);
    }

    public ProductoDTO productoFallback(Long id, Throwable ex) {
        System.out.println("Fallback producto-service ejecutado (" + ex.getClass().getSimpleName() + "): " + ex.getMessage());
        ProductoDTO dto = new ProductoDTO();
        dto.setId(id);
        dto.setNombre("Producto temporal no disponible");
        dto.setPrecio(0.0);
        dto.setStock(0);
        return dto;
    }
}
