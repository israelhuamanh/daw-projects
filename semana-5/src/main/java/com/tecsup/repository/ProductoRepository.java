package com.tecsup.repository;

import com.tecsup.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Método solicitado en la Actividad para búsqueda por nombre (flexible, insensible a mayúsculas)
    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    // Método complementario para filtrar por categoría
    List<Producto> findByCategoriaIgnoreCase(String categoria);
}
