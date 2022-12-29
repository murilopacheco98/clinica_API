package com.growdev.ecommerce.repositories;

import com.growdev.ecommerce.entities.Especialidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long> {
   Especialidade findByNome(String nome);
}
