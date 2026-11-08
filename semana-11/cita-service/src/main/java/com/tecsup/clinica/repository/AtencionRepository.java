package com.tecsup.clinica.repository;

import com.tecsup.clinica.model.Atencion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AtencionRepository extends JpaRepository<Atencion, Long> {
    Optional<Atencion> findByCitaIdCita(Long idCita);
}
