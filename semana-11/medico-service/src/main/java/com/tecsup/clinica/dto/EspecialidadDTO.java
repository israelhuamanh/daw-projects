package com.tecsup.clinica.dto;

import jakarta.validation.constraints.NotBlank;

public class EspecialidadDTO {

    @NotBlank(message = "El nombre de la especialidad es obligatorio")
    private String nombre;

    private String descripcion;
    private String estado = "A";

    public EspecialidadDTO() {}

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
