package com.growdev.ecommerce.repositories;

import com.growdev.ecommerce.entities.user.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    @Query("SELECT objeto FROM Paciente objeto where objeto.usuario.email = :email")
    Paciente findByEmail(String email);
    Paciente findByCpf(String cpf);
}
