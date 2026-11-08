package com.tecsup.clinica.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public class CitaDTO {

    @NotNull(message = "La fecha de la cita es obligatoria")
    private LocalDate fecha;

    @NotNull(message = "La hora de la cita es obligatoria")
    private LocalTime hora;

    @NotNull(message = "El ID del paciente es obligatorio")
    private Long idPaciente;

    @NotNull(message = "El ID del médico es obligatorio")
    private Long idMedico;

    private String motivo;

    public CitaDTO() {}

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
}
