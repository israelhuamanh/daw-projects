package com.tecsup.services;

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
    private ClienteCircuitService clienteCircuitService;

    @Autowired
    private ProductoCircuitService productoCircuitService;

    public List<Pedido> listar() {
        List<Pedido> lista = repo.findAll();
        for (Pedido p : lista) {
            completarInformacion(p);
        }
        return lista;
    }

    public Pedido guardar(PedidoDTO dto) {
        ProductoDTO producto = productoCircuitService.obtenerProducto(dto.getProductoId());
        ClienteDTO cliente = clienteCircuitService.obtenerCliente(dto.getClienteId());

        Pedido p = new Pedido();
        p.setClienteId(dto.getClienteId());
        p.setProductoId(dto.getProductoId());
        p.setCantidad(dto.getCantidad());
        p.setFecha(LocalDate.now());
        double precio = (producto != null && producto.getPrecio() != null) ? producto.getPrecio() : 0.0;
        p.setTotal(precio * dto.getCantidad());
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
        ClienteDTO c = clienteCircuitService.obtenerCliente(p.getClienteId());
        p.setCliente(c);

        ProductoDTO prod = productoCircuitService.obtenerProducto(p.getProductoId());
        p.setProducto(prod);
    }
}
