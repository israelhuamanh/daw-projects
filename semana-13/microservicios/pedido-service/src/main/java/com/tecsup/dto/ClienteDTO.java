package com.tecsup.dto;

public class ClienteDTO {
    private Long id;
    private String nombre;
    private String apellidos;
    private String direccion;
    private String telefono;
    private String nroDni;
    private String estado;

    public ClienteDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getNroDni() { return nroDni; }
    public void setNroDni(String nroDni) { this.nroDni = nroDni; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
