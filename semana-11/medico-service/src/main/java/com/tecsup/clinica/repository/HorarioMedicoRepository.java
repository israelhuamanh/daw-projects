package com.tecsup.clinica.repository;

import com.tecsup.clinica.model.HorarioMedico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HorarioMedicoRepository extends JpaRepository<HorarioMedico, Long> {
    List<HorarioMedico> findByMedicoIdMedico(Long idMedico);
}
