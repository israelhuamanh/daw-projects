package com.tecsup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.dto.ProductoDTO;
import com.tecsup.model.Producto;
import com.tecsup.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LaboratorioSemana5Tests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductoRepository repo;

    @BeforeEach
    void setUp() {
        repo.deleteAll();
    }

    @Test
    @DisplayName("POST - Debe guardar producto con datos válidos (Status 201)")
    void testGuardarProductoValido() throws Exception {
        ProductoDTO dto = new ProductoDTO("Monitor Gamer 144Hz", 350.0, 15, "Periféricos");

        mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nombre").value("Monitor Gamer 144Hz"))
                .andExpect(jsonPath("$.precio").value(350.0))
                .andExpect(jsonPath("$.stock").value(15))
                .andExpect(jsonPath("$.categoria").value("Periféricos"));
    }

    @Test
    @DisplayName("POST - Debe fallar validación con nombre en blanco (Status 400)")
    void testValidacionNombreInvalido() throws Exception {
        ProductoDTO dto = new ProductoDTO("", 250.0, 10, "Tecnología");

        mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errores.nombre", containsString("El nombre es obligatorio")));
    }

    @Test
    @DisplayName("POST - Debe fallar validación con precio negativo (Status 400)")
    void testValidacionPrecioInvalido() throws Exception {
        ProductoDTO dto = new ProductoDTO("Teclado Mecánico", -50.0, 10, "Periféricos");

        mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errores.precio", containsString("El precio debe ser mayor a 0")));
    }

    @Test
    @DisplayName("POST - Debe fallar validación con stock negativo (Status 400)")
    void testValidacionStockInvalido() throws Exception {
        ProductoDTO dto = new ProductoDTO("Mouse Gamer", 80.0, -3, "Periféricos");

        mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errores.stock", containsString("El stock no puede ser negativo")));
    }

    @Test
    @DisplayName("POST - Debe fallar validación con categoría vacía (Status 400)")
    void testValidacionCategoriaInvalida() throws Exception {
        ProductoDTO dto = new ProductoDTO("Auriculares", 120.0, 5, "");

        mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errores.categoria", containsString("La categoría es obligatoria")));
    }

    @Test
    @DisplayName("GET - Buscar producto por nombre (Actividad Semana 5)")
    void testBuscarPorNombre() throws Exception {
        repo.save(new Producto(null, "Laptop ASUS TUF", 1200.0, 8, "Laptops"));
        repo.save(new Producto(null, "Laptop Lenovo IdeaPad", 900.0, 12, "Laptops"));
        repo.save(new Producto(null, "Mouse Inalámbrico", 30.0, 50, "Accesorios"));

        // Buscar 'laptop'
        mockMvc.perform(get("/api/productos/buscar").param("nombre", "laptop"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nombre", containsString("Laptop")))
                .andExpect(jsonPath("$[1].nombre", containsString("Laptop")));
    }

    @Test
    @DisplayName("DELETE - Eliminar producto por ID y verificar 404")
    void testEliminarProducto() throws Exception {
        Producto guardado = repo.save(new Producto(null, "Monitor Dell", 200.0, 5, "Monitores"));

        mockMvc.perform(delete("/api/productos/" + guardado.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/productos/" + guardado.getId()))
                .andExpect(status().isNotFound());
    }
}
