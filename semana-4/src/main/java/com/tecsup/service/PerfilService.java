package com.tecsup.service;

import com.tecsup.models.Perfil;
import com.tecsup.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerfilService {

    @Autowired
    private PerfilRepository perfilRepo;

    public List<Perfil> listar() {
        return perfilRepo.findAll();
    }

    public Perfil obtener(Long id) {
        return perfilRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado con ID: " + id));
    }

    public Perfil guardar(Perfil perfil) {
        return perfilRepo.save(perfil);
    }
}
