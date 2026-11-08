package com.tecsup.clinica.dto;

import jakarta.validation.constraints.NotBlank;

public class AtencionDTO {

    @NotBlank(message = "El diagnóstico es obligatorio")
    private String diagnostico;

    private String tratamiento;
    private String observaciones;

    public AtencionDTO() {}

    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }

    public String getTratamiento() { return tratamiento; }
    public void setTratamiento(String tratamiento) { this.tratamiento = tratamiento; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}
