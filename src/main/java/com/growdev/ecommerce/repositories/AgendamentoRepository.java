package com.growdev.ecommerce.repositories;

import com.growdev.ecommerce.entities.Agendamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
//    Agendamento findByName(String nome);

    @Query("SELECT objeto FROM Agendamento objeto WHERE objeto.paciente.id = :id")
    Page<Agendamento> findByPacienteId(Long id, Pageable pageable);

    @Query("SELECT objeto FROM Agendamento objeto WHERE objeto.medico.id = :id")
    Page<Agendamento> findByMedicoId(Long id, Pageable pageable);
}
