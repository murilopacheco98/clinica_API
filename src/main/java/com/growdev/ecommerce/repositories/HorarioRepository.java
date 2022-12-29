package com.growdev.ecommerce.repositories;

import com.growdev.ecommerce.entities.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {
    Horario findByHoraMinuto(LocalTime horaMinuto);
}
