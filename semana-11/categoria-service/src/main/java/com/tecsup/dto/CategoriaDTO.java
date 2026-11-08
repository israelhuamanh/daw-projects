package com.tecsup.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoriaDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    public CategoriaDTO() {}

    public CategoriaDTO(String nombre, String estado) {
        this.nombre = nombre;
        this.estado = estado;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
