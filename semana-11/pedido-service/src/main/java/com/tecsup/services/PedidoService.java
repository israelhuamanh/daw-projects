package com.tecsup.services;

import com.tecsup.model.DetallePedido;
import com.tecsup.model.Pedido;
import com.tecsup.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    public List<Pedido> listar() {
        return repository.findAll();
    }

    public Optional<Pedido> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Pedido guardar(Pedido pedido) {
        if (pedido.getDetalles() != null) {
            for (DetallePedido detalle : pedido.getDetalles()) {
                detalle.setPedido(pedido);
            }
        }
        return repository.save(pedido);
    }

    public Pedido actualizar(Long id, Pedido pedido) {
        Pedido pedidoExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        pedidoExistente.setFecha(pedido.getFecha());
        pedidoExistente.setIdCliente(pedido.getIdCliente());
        pedidoExistente.setTotal(pedido.getTotal());

        return repository.save(pedidoExistente);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
