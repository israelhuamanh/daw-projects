package com.tecsup.clinica.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "cita")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita")
    private Long idCita;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private LocalTime hora;

    @Column(name = "id_paciente", nullable = false)
    private Long idPaciente;

    @Column(name = "id_medico", nullable = false)
    private Long idMedico;

    @Column(length = 300)
    private String motivo;

    @Column(length = 20)
    private String estado = "PROGRAMADA"; // PROGRAMADA, CONFIRMADA, ATENDIDA, CANCELADA

    @OneToOne(mappedBy = "cita", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Atencion atencion;

    public Cita() {}

    public Long getIdCita() { return idCita; }
    public void setIdCita(Long idCita) { this.idCita = idCita; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }

    public Long getIdPaciente() { return idPaciente; }
    public void setIdPaciente(Long idPaciente) { this.idPaciente = idPaciente; }

    public Long getIdMedico() { return idMedico; }
    public void setIdMedico(Long idMedico) { this.idMedico = idMedico; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Atencion getAtencion() { return atencion; }
    public void setAtencion(Atencion atencion) { this.atencion = atencion; }
}
