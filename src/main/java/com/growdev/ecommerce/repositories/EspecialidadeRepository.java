package com.growdev.ecommerce.repositories;

import com.growdev.ecommerce.entities.Especialidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long> {
   Especialidade findByNome(String nome);
   List<Especialidade> findAllByOrderByNomeAsc();
   @Query(value = "SELECT objeto FROM Especialidade objeto WHERE objeto.nome LIKE %:search%")
   Page<Especialidade> findAllByNome(String search, Pageable pageable);
}
