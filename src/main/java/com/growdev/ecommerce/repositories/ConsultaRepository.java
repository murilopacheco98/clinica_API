package com.growdev.ecommerce.repositories;

import com.growdev.ecommerce.entities.Consulta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    @Query("SELECT objeto FROM Consulta objeto WHERE objeto.paciente.id = :id")
    Page<Consulta> findByPacienteId(Long id, Pageable pageable);

    @Query("SELECT objeto FROM Consulta objeto WHERE objeto.medico.id = :id")
    Page<Consulta> findByMedicoId(Long id, Pageable pageable);

}
