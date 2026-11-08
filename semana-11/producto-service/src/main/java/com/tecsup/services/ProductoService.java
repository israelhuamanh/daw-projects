package com.tecsup.services;

import com.tecsup.exception.ResourceNotFoundException;
import com.tecsup.model.Producto;
import com.tecsup.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository repo;

    public List<Producto> listar() {
        return repo.findAll();
    }

    public Producto guardar(Producto p) {
        return repo.save(p);
    }

    public Producto obtener(Long id) {
        return repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
    }

    public List<Producto> listarPorCategoria(Long categoriaId) {
        return repo.findByCategoriaId(categoriaId);
    }

    public void eliminar(Long id) {
        Producto p = repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
        repo.delete(p);
    }
}
