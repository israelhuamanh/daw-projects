package com.tecsup.services;

import com.tecsup.client.ClienteClient;
import com.tecsup.dto.ClienteDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteCircuitService {

    @Autowired
    private ClienteClient clienteClient;

    @CircuitBreaker(name = "clienteService")
    @Retry(name = "clienteRetry", fallbackMethod = "clienteFallback")
    public ClienteDTO obtenerCliente(Long id) {
        System.out.println("Llamando a cliente-service... id=" + id);
        return clienteClient.obtenerCliente(id);
    }

    public ClienteDTO clienteFallback(Long id, Throwable ex) {
        System.out.println("Fallback cliente-service ejecutado (" + ex.getClass().getSimpleName() + "): " + ex.getMessage());
        ClienteDTO dto = new ClienteDTO();
        dto.setId(id);
        dto.setNombre("Cliente temporal no disponible");
        dto.setApellidos("-");
        dto.setEstado("NO_DISPONIBLE");
        return dto;
    }
}
