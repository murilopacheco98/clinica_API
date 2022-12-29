package com.growdev.ecommerce.repositories;

import com.growdev.ecommerce.entities.user.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Medico findByNomeJaleco(String nomeJaleco);
}
