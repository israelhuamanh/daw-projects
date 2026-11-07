package com.tecsup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.tecsup.models.Producto;
import com.tecsup.models.Categoria;
import com.tecsup.repository.ProductoRepository;
import com.tecsup.repository.CategoriaRepository;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepo;

    @Autowired
    private CategoriaRepository categoriaRepo;

    public List<Producto> listar() {
        return productoRepo.findAll();
    }

    public Producto guardar(Producto producto) {
        if (producto.getCategoria() != null && producto.getCategoria().getId() != null) {
            Long categoriaId = producto.getCategoria().getId();
            Categoria categoria = categoriaRepo.findById(categoriaId)
                    .orElseThrow(() -> new RuntimeException("Categoría no existe"));
            producto.setCategoria(categoria);
        }
        return productoRepo.save(producto);
    }

    public Producto obtener(Long id) {
        return productoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public Producto actualizar(Long id, Producto nuevo) {
        Producto existente = obtener(id);
        existente.setNombre(nuevo.getNombre());
        existente.setPrecio(nuevo.getPrecio());

        if (nuevo.getCategoria() != null && nuevo.getCategoria().getId() != null) {
            Long categoriaId = nuevo.getCategoria().getId();
            Categoria categoria = categoriaRepo.findById(categoriaId)
                    .orElseThrow(() -> new RuntimeException("Categoría no existe"));
            existente.setCategoria(categoria);
        }
        return productoRepo.save(existente);
    }

    public void eliminar(Long id) {
        productoRepo.deleteById(id);
    }

    public List<Producto> listarPorCategoria(Long categoriaId) {
        return productoRepo.findByCategoriaId(categoriaId);
    }
}
