package com.tecsup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.dto.LoginRequest;
import com.tecsup.dto.SignupRequest;
import com.tecsup.entity.Role;
import com.tecsup.repository.RoleRepository;
import com.tecsup.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LaboratorioSemana9Tests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        // Asegurar que existan los roles base
        if (roleRepository.findByName("USER").isEmpty()) {
            roleRepository.save(new Role(null, "USER"));
        }
        if (roleRepository.findByName("ADMIN").isEmpty()) {
            roleRepository.save(new Role(null, "ADMIN"));
        }
        if (roleRepository.findByName("MODERATOR").isEmpty()) {
            roleRepository.save(new Role(null, "MODERATOR"));
        }
    }

    // ==========================================
    // CASO 1: Ruta Pública
    // ==========================================
    @Test
    @DisplayName("Caso 1: Ruta pública /api/test/all no requiere token (Status 200)")
    void testRutaPublica() throws Exception {
        mockMvc.perform(get("/api/test/all"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Public Content")));
    }

    // ==========================================
    // CASO 2: Acceso sin token
    // ==========================================
    @Test
    @DisplayName("Caso 2: Acceso sin token a /api/test/user es rechazado (Status 401)")
    void testAccesoSinToken() throws Exception {
        mockMvc.perform(get("/api/test/user"))
                .andExpect(status().isUnauthorized());
    }

    // ==========================================
    // CASO 3: Acceso con token inválido
    // ==========================================
    @Test
    @DisplayName("Caso 3: Acceso con token alterado o inválido es rechazado (Status 401)")
    void testAccesoTokenInvalido() throws Exception {
        String tokenInvalido = "Bearer eyJhbGciOiJIUzI1NiJ9.tokenFalsoManipulado.firmaInvalida123";

        mockMvc.perform(get("/api/test/user")
                        .header("Authorization", tokenInvalido))
                .andExpect(status().isUnauthorized());
    }

    // ==========================================
    // CASO 4: Acceso con token válido (USER)
    // ==========================================
    @Test
    @DisplayName("Caso 4: Acceso con token válido a /api/test/user (Status 200)")
    void testAccesoTokenValidoUser() throws Exception {
        // 1. Registro
        SignupRequest signup = new SignupRequest("user1", "user@test.com", "123456", Set.of("USER"));
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signup)))
                .andExpect(status().isOk());

        // 2. Login
        LoginRequest login = new LoginRequest("user@test.com", "123456");
        MvcResult result = mockMvc.perform(post("/api/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String token = objectMapper.readTree(responseBody).get("accessToken").asText();

        // 3. Acceso autorizado
        mockMvc.perform(get("/api/test/user")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string("User Content"));
    }

    // ==========================================
    // CASO 5: Acceso a ruta ADMIN sin permisos (Rol USER)
    // ==========================================
    @Test
    @DisplayName("Caso 5: Usuario con rol USER intentando acceder a /api/test/admin (Status 403 Forbidden)")
    void testAccesoAdminSinPermisos() throws Exception {
        // 1. Registro USER
        SignupRequest signup = new SignupRequest("userNormal", "normal@test.com", "123456", Set.of("USER"));
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signup)))
                .andExpect(status().isOk());

        // 2. Login
        LoginRequest login = new LoginRequest("normal@test.com", "123456");
        MvcResult result = mockMvc.perform(post("/api/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andReturn();

        String token = objectMapper.readTree(result.getResponse().getContentAsString()).get("accessToken").asText();

        // 3. Intento de acceso a ruta ADMIN -> 403 Forbidden
        mockMvc.perform(get("/api/test/admin")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    // ==========================================
    // CASO 6: Acceso a ruta ADMIN con permisos (Rol ADMIN)
    // ==========================================
    @Test
    @DisplayName("Caso 6: Usuario con rol ADMIN accediendo a /api/test/admin (Status 200 OK)")
    void testAccesoAdminConPermisos() throws Exception {
        // 1. Registro ADMIN
        SignupRequest signup = new SignupRequest("adminMaster", "admin@test.com", "123456", Set.of("ADMIN"));
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signup)))
                .andExpect(status().isOk());

        // 2. Login
        LoginRequest login = new LoginRequest("admin@test.com", "123456");
        MvcResult result = mockMvc.perform(post("/api/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andReturn();

        String token = objectMapper.readTree(result.getResponse().getContentAsString()).get("accessToken").asText();

        // 3. Acceso autorizado como ADMIN -> 200 OK
        mockMvc.perform(get("/api/test/admin")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string("Admin Content"));
    }
}
