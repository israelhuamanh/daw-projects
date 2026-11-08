package com.tecsup.services;

import com.tecsup.client.ClienteClient;
import com.tecsup.client.ProductoClient;
import com.tecsup.dto.ClienteDTO;
import com.tecsup.dto.PedidoDTO;
import com.tecsup.dto.ProductoDTO;
import com.tecsup.exception.ResourceNotFoundException;
import com.tecsup.model.Pedido;
import com.tecsup.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repo;

    @Autowired
    private ClienteClient clienteClient;

    @Autowired
    private ProductoClient productoClient;

    public List<Pedido> listar() {
        List<Pedido> lista = repo.findAll();
        for (Pedido p : lista) {
            completarInformacion(p);
        }
        return lista;
    }

    public Pedido guardar(PedidoDTO dto) {
        // 1. Validar y obtener producto vía Feign
        ProductoDTO producto = productoClient.obtenerProducto(dto.getProductoId());
        if (producto == null) {
            throw new ResourceNotFoundException("Producto no encontrado en producto-service con ID: " + dto.getProductoId());
        }

        // 2. Validar y obtener cliente vía Feign
        ClienteDTO cliente = clienteClient.obtenerCliente(dto.getClienteId());
        if (cliente == null) {
            throw new ResourceNotFoundException("Cliente no encontrado en cliente-service con ID: " + dto.getClienteId());
        }

        // 3. Crear entidad Pedido sin duplicar datos
        Pedido p = new Pedido();
        p.setClienteId(dto.getClienteId());
        p.setProductoId(dto.getProductoId());
        p.setCantidad(dto.getCantidad());
        p.setFecha(LocalDate.now());
        p.setTotal(producto.getPrecio() * dto.getCantidad());
        p.setEstado("PENDIENTE");

        Pedido guardado = repo.save(p);
        guardado.setCliente(cliente);
        guardado.setProducto(producto);
        return guardado;
    }

    public Pedido obtener(Long id) {
        Pedido p = repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Pedido no encontrado con ID: " + id));
        completarInformacion(p);
        return p;
    }

    public Pedido actualizarEstado(Long id, String nuevoEstado) {
        Pedido p = obtener(id);
        p.setEstado(nuevoEstado.trim().toUpperCase());
        return repo.save(p);
    }

    public Pedido anular(Long id) {
        Pedido p = obtener(id);
        p.setEstado("ANULADO");
        return repo.save(p);
    }

    private void completarInformacion(Pedido p) {
        try {
            ClienteDTO c = clienteClient.obtenerCliente(p.getClienteId());
            p.setCliente(c);
        } catch (Exception e) {
            // En caso de servicio no disponible momentáneamente
            p.setCliente(null);
        }

        try {
            ProductoDTO prod = productoClient.obtenerProducto(p.getProductoId());
            p.setProducto(prod);
        } catch (Exception e) {
            p.setProducto(null);
        }
    }
}
