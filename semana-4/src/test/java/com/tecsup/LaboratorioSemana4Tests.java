package com.tecsup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.models.*;
import com.tecsup.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LaboratorioSemana4Tests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoriaRepository categoriaRepo;

    @Autowired
    private ProductoRepository productoRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private PerfilRepository perfilRepo;

    @Autowired
    private EstudianteRepository estudianteRepo;

    @Autowired
    private CursoRepository cursoRepo;

    @BeforeEach
    void setUp() {
        productoRepo.deleteAll();
        categoriaRepo.deleteAll();
        usuarioRepo.deleteAll();
        perfilRepo.deleteAll();
        estudianteRepo.deleteAll();
        cursoRepo.deleteAll();
    }

    // ==========================================
    // PARTE 1: PRUEBAS RELACIÓN 1:N (@OneToMany / @ManyToOne)
    // ==========================================
    @Test
    @DisplayName("1:N - Debe crear categoría, producto asociado y listar por categoría")
    void testCategoriaYProductoRelacion() throws Exception {
        // 1. Crear Categoría
        Categoria cat = new Categoria(null, "Tecnología");
        String catJson = mockMvc.perform(post("/api/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cat)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nombre").value("Tecnología"))
                .andReturn().getResponse().getContentAsString();

        Categoria catCreada = objectMapper.readValue(catJson, Categoria.class);

        // 2. Crear Producto asociado a Categoría
        Producto prod = new Producto(null, "Laptop Gamer Lenovo", 3500.0, catCreada);
        mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(prod)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nombre").value("Laptop Gamer Lenovo"))
                .andExpect(jsonPath("$.precio").value(3500.0))
                .andExpect(jsonPath("$.categoria.id").value(catCreada.getId()));

        // 3. Listar productos por categoría
        mockMvc.perform(get("/api/productos/categoria/" + catCreada.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nombre").value("Laptop Gamer Lenovo"));
    }

    // ==========================================
    // PARTE 2: PRUEBAS RELACIÓN 1:1 (@OneToOne)
    // ==========================================
    @Test
    @DisplayName("1:1 - Debe registrar usuario con perfil en cascada y eliminar en cascada")
    void testUsuarioYPerfilCascade() throws Exception {
        // 1. Registrar usuario con perfil
        Perfil perfil = new Perfil(null, "Israel Angel Huaman Huaman", "Av. Las Palmeras 123", "987654321");
        Usuario usuario = new Usuario(null, "israelhuaman", "Password123!", perfil);

        String userJson = mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.username").value("israelhuaman"))
                .andExpect(jsonPath("$.perfil.nombreCompleto").value("Israel Angel Huaman Huaman"))
                .andExpect(jsonPath("$.perfil.telefono").value("987654321"))
                .andReturn().getResponse().getContentAsString();

        Usuario usuarioCreado = objectMapper.readValue(userJson, Usuario.class);
        Long usuarioId = usuarioCreado.getId();
        Long perfilId = usuarioCreado.getPerfil().getId();

        // 2. Obtener usuario por ID
        mockMvc.perform(get("/api/usuarios/" + usuarioId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("israelhuaman"))
                .andExpect(jsonPath("$.perfil.id").value(perfilId));

        // 3. Eliminar usuario y verificar cascada de perfil
        mockMvc.perform(delete("/api/usuarios/" + usuarioId))
                .andExpect(status().isNoContent());

        assertFalse(usuarioRepo.existsById(usuarioId), "El usuario debió ser eliminado");
        assertFalse(perfilRepo.existsById(perfilId), "El perfil debió eliminarse en cascada automáticamente");
    }

    // ==========================================
    // PARTE 3: PRUEBAS RELACIÓN N:M (@ManyToMany)
    // ==========================================
    @Test
    @DisplayName("N:M - Debe asociar cursos a estudiante y validar que no se repita inscripción")
    void testEstudianteYCursosManyToMany() throws Exception {
        // 1. Crear Cursos
        Curso c1 = cursoRepo.save(new Curso(null, "Desarrollo de Aplicaciones Web", 4));
        Curso c2 = cursoRepo.save(new Curso(null, "Base de Datos Avanzado", 3));

        // 2. Crear Estudiante
        Estudiante est = new Estudiante(null, "Israel Angel Huaman Huaman", "israel.huaman@tecsup.edu.pe");
        String estJson = mockMvc.perform(post("/api/estudiantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(est)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andReturn().getResponse().getContentAsString();

        Estudiante estCreado = objectMapper.readValue(estJson, Estudiante.class);
        Long estId = estCreado.getId();

        // 3. Inscribir estudiante en curso 1
        mockMvc.perform(post("/api/estudiantes/" + estId + "/cursos/" + c1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cursos", hasSize(1)))
                .andExpect(jsonPath("$.cursos[0].nombre").value("Desarrollo de Aplicaciones Web"));

        // 4. Intentar inscribir de nuevo en el mismo curso (VALIDACIÓN REGLA DE NEGOCIO: No repetir)
        mockMvc.perform(post("/api/estudiantes/" + estId + "/cursos/" + c1.getId()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("ya se encuentra inscrito")));

        // 5. Inscribir en curso 2
        mockMvc.perform(post("/api/estudiantes/" + estId + "/cursos/" + c2.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cursos", hasSize(2)));

        // 6. Listar cursos del estudiante
        mockMvc.perform(get("/api/estudiantes/" + estId + "/cursos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        // 7. Quitar un curso
        mockMvc.perform(delete("/api/estudiantes/" + estId + "/cursos/" + c1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cursos", hasSize(1)));
    }
}
