package com.tecsup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class ConfigController {

    // 1. Inyección de propiedades mediante @Value (solicitado en el laboratorio)
    @Value("${empresa.ambiente:DESARROLLO}")
    private String ambiente;

    @Value("${empresa.perfil:dev}")
    private String perfil;

    @Value("${server.port:8080}")
    private String puerto;

    @Value("${empresa.basedatos:empleados_dev}")
    private String baseDatos;

    // 2. Acceso a propiedades mediante la interfaz Environment (solicitado en el laboratorio)
    @Autowired
    private Environment environment;

    // Endpoint solicitado en la Parte 5 de la Actividad: GET /configuracion
    @GetMapping("/configuracion")
    public ResponseEntity<Map<String, String>> obtenerConfiguracionCompleta() {
        Map<String, String> config = new LinkedHashMap<>();
        config.put("ambiente", ambiente);
        config.put("perfil", perfil);
        config.put("puerto", puerto);
        config.put("baseDatos", baseDatos);
        return ResponseEntity.ok(config);
    }

    // Endpoint simple de verificación del laboratorio inicial: GET /config
    @GetMapping("/config")
    public ResponseEntity<String> obtenerAmbienteSimple() {
        String activeProfile = environment.getActiveProfiles().length > 0 
                ? environment.getActiveProfiles()[0] 
                : "dev (default)";
        return ResponseEntity.ok("Ambiente activo: " + ambiente + " | Perfil activo: " + activeProfile + " | Puerto: " + puerto);
    }
}
