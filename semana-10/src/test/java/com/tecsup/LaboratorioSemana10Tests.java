package com.tecsup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.model.Empleado;
import com.tecsup.repository.EmpleadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class LaboratorioSemana10Tests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmpleadoRepository repo;

    @BeforeEach
    void setUp() {
        repo.deleteAll();
    }

    // ==========================================
    // 1. Pruebas de Endpoints de Configuración
    // ==========================================
    @Test
    @DisplayName("GET /configuracion - Debe retornar el JSON exacto con variables del perfil activo")
    void testEndpointConfiguracion() throws Exception {
        mockMvc.perform(get("/configuracion"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ambiente").value("DESARROLLO"))
                .andExpect(jsonPath("$.perfil").value("dev"))
                .andExpect(jsonPath("$.puerto").value("8080"))
                .andExpect(jsonPath("$.baseDatos").value("empleados_dev"));
    }

    @Test
    @DisplayName("GET /config - Debe retornar cadena simple con ambiente activo")
    void testEndpointConfigSimple() throws Exception {
        mockMvc.perform(get("/config"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("DESARROLLO")));
    }

    // ==========================================
    // 2. Pruebas de CRUD REST de Empleados
    // ==========================================
    @Test
    @DisplayName("CRUD - Debe registrar, listar, obtener, actualizar y eliminar empleado")
    void testCrudEmpleadoCompleto() throws Exception {
        // 1. POST
        Empleado emp = new Empleado(null, "Israel Angel", "Huaman Huaman", "israel.huaman@techsolutions.com", new BigDecimal("4500.00"));
        String resJson = mockMvc.perform(post("/api/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emp)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nombres").value("Israel Angel"))
                .andReturn().getResponse().getContentAsString();

        Empleado guardado = objectMapper.readValue(resJson, Empleado.class);
        Long id = guardado.getId();

        // 2. GET por ID
        mockMvc.perform(get("/api/empleados/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("israel.huaman@techsolutions.com"));

        // 3. PUT Actualizar
        guardado.setSueldo(new BigDecimal("5000.00"));
        mockMvc.perform(put("/api/empleados/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(guardado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sueldo").value(5000.00));

        // 4. GET Todos
        mockMvc.perform(get("/api/empleados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        // 5. DELETE
        mockMvc.perform(delete("/api/empleados/" + id))
                .andExpect(status().isOk());

        // 6. Verificar 404
        mockMvc.perform(get("/api/empleados/" + id))
                .andExpect(status().isNotFound());
    }
}
