package com.tecsup.clinica.services;

import com.tecsup.clinica.exception.ResourceNotFoundException;
import com.tecsup.clinica.model.HorarioMedico;
import com.tecsup.clinica.model.Medico;
import com.tecsup.clinica.repository.HorarioMedicoRepository;
import com.tecsup.clinica.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository repo;

    @Autowired
    private HorarioMedicoRepository horarioRepo;

    public List<Medico> listar() {
        return repo.findAll();
    }

    public Medico guardar(Medico m) {
        return repo.save(m);
    }

    public Medico obtenerPorId(Long id) {
        return repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Médico no encontrado con id: " + id));
    }

    public void eliminar(Long id) {
        Medico m = obtenerPorId(id);
        repo.delete(m);
    }

    public HorarioMedico agregarHorario(Long idMedico, HorarioMedico horario) {
        Medico m = obtenerPorId(idMedico);
        horario.setMedico(m);
        return horarioRepo.save(horario);
    }

    public List<HorarioMedico> listarHorariosPorMedico(Long idMedico) {
        return horarioRepo.findByMedicoIdMedico(idMedico);
    }
}
