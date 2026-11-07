package com.tecsup.service;

import com.tecsup.models.Perfil;
import com.tecsup.models.Usuario;
import com.tecsup.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepo;

    public List<Usuario> listar() {
        return usuarioRepo.findAll();
    }

    public Usuario obtener(Long id) {
        return usuarioRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    @Transactional
    public Usuario registrar(Usuario usuario) {
        // La relación @OneToOne con CascadeType.ALL guardará el perfil automáticamente
        return usuarioRepo.save(usuario);
    }

    @Transactional
    public Usuario actualizar(Long id, Usuario nuevo) {
        Usuario existente = obtener(id);
        existente.setUsername(nuevo.getUsername());
        if (nuevo.getPassword() != null && !nuevo.getPassword().isBlank()) {
            existente.setPassword(nuevo.getPassword());
        }

        if (nuevo.getPerfil() != null) {
            Perfil perfilExistente = existente.getPerfil();
            if (perfilExistente == null) {
                perfilExistente = new Perfil();
            }
            perfilExistente.setNombreCompleto(nuevo.getPerfil().getNombreCompleto());
            perfilExistente.setDireccion(nuevo.getPerfil().getDireccion());
            perfilExistente.setTelefono(nuevo.getPerfil().getTelefono());
            existente.setPerfil(perfilExistente);
        }

        return usuarioRepo.save(existente);
    }

    @Transactional
    public void eliminar(Long id) {
        Usuario usuario = obtener(id);
        usuarioRepo.delete(usuario);
    }
}
