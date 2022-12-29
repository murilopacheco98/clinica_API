package com.growdev.ecommerce.repositories;

import com.growdev.ecommerce.entities.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
//    Agendamento findByName(String nome);
}
