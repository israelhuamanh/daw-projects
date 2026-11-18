package com.tecsup.services;

import com.tecsup.client.CategoriaClient;
import com.tecsup.dto.CategoriaDTO;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaCircuitService {

    @Autowired
    private CategoriaClient categoriaClient;

    @Bulkhead(name = "categoriaBulkhead")
    @CircuitBreaker(name = "categoriaService")
    @Retry(name = "categoriaRetry", fallbackMethod = "categoriaFallback")
    public CategoriaDTO obtenerCategoria(Long id) {
        System.out.println("Llamando a categoria-service... id=" + id);
        return categoriaClient.obtenerCategoria(id);
    }

    public CategoriaDTO categoriaFallback(Long id, Throwable ex) {
        System.out.println("Circuit Breaker / Resiliencia activado (Fallback ejecutado): " + ex.getClass().getSimpleName() + " - " + ex.getMessage());
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(id);
        dto.setNombre("Categoría temporal no disponible");
        return dto;
    }
}
