package com.tecsup.clinica.services;

import com.tecsup.clinica.exception.BusinessException;
import com.tecsup.clinica.exception.ResourceNotFoundException;
import com.tecsup.clinica.model.Atencion;
import com.tecsup.clinica.model.Cita;
import com.tecsup.clinica.repository.AtencionRepository;
import com.tecsup.clinica.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class CitaService {

    private static final List<String> ESTADOS_VALIDOS = Arrays.asList(
            "PROGRAMADA", "CONFIRMADA", "ATENDIDA", "CANCELADA"
    );

    @Autowired
    private CitaRepository repo;

    @Autowired
    private AtencionRepository atencionRepo;

    public List<Cita> listar() {
        return repo.findAll();
    }

    public Cita obtenerPorId(Long id) {
        return repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Cita no encontrada con id: " + id));
    }

    public Cita registrar(Cita cita) {
        cita.setEstado("PROGRAMADA");
        return repo.save(cita);
    }

    public Cita actualizarEstado(Long idCita, String nuevoEstado) {
        Cita cita = obtenerPorId(idCita);
        String estadoUpper = nuevoEstado.trim().toUpperCase();

        if (!ESTADOS_VALIDOS.contains(estadoUpper)) {
            throw new BusinessException("Estado no válido. Estados permitidos: " + ESTADOS_VALIDOS);
        }

        if ("ATENDIDA".equals(cita.getEstado())) {
            throw new BusinessException("No se puede modificar una cita que ya ha sido ATENDIDA.");
        }

        if ("CANCELADA".equals(cita.getEstado())) {
            throw new BusinessException("No se puede modificar una cita que ya fue CANCELADA.");
        }

        cita.setEstado(estadoUpper);
        return repo.save(cita);
    }

    public Cita cancelar(Long idCita) {
        Cita cita = obtenerPorId(idCita);

        if ("ATENDIDA".equals(cita.getEstado())) {
            throw new BusinessException("No es posible cancelar una cita que ya fue ATENDIDA.");
        }

        if ("CANCELADA".equals(cita.getEstado())) {
            throw new BusinessException("La cita ya se encuentra CANCELADA.");
        }

        cita.setEstado("CANCELADA");
        return repo.save(cita);
    }

    public Atencion registrarAtencion(Long idCita, Atencion atencion) {
        Cita cita = obtenerPorId(idCita);

        if ("CANCELADA".equals(cita.getEstado())) {
            throw new BusinessException("No se puede registrar atención en una cita CANCELADA.");
        }

        if ("ATENDIDA".equals(cita.getEstado()) && cita.getAtencion() != null) {
            throw new BusinessException("Esta cita ya cuenta con atención médica registrada.");
        }

        atencion.setCita(cita);
        atencion.setFechaRegistro(LocalDateTime.now());
        cita.setEstado("ATENDIDA");
        cita.setAtencion(atencion);

        Atencion guardada = atencionRepo.save(atencion);
        repo.save(cita);
        return guardada;
    }

    public Atencion obtenerAtencionPorCita(Long idCita) {
        return atencionRepo.findByCitaIdCita(idCita).orElseThrow(
                () -> new ResourceNotFoundException("No se encontró atención registrada para la cita ID: " + idCita));
    }
}
