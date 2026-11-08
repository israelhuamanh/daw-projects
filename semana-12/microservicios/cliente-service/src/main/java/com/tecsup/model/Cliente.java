package com.tecsup.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellidos;

    @Column(nullable = false, length = 200)
    private String direccion;

    @Column(length = 20)
    private String telefono;

    @Column(name = "nro_dni", nullable = false, length = 8, unique = true)
    private String nroDni;

    @Column(nullable = false, length = 20)
    private String estado = "ACTIVO";

    public Cliente() {}

    public Cliente(Long id, String nombre, String apellidos, String direccion, String telefono, String nroDni, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefono = telefono;
        this.nroDni = nroDni;
        this.estado = estado;
    }

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
