package com.tecsup.controllers;

import com.tecsup.models.Perfil;
import com.tecsup.service.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/perfiles")
public class PerfilController {

    @Autowired
    private PerfilService service;

    @GetMapping
    public List<Perfil> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Perfil obtener(@PathVariable Long id) {
        return service.obtener(id);
    }
}
