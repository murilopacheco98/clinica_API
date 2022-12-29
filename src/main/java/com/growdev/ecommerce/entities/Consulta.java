package com.growdev.ecommerce.entities;

import com.growdev.ecommerce.dto.ConsultaDTO;
import com.growdev.ecommerce.entities.user.Medico;
import com.growdev.ecommerce.entities.user.Paciente;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "consulta")
public class Consulta implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String diagnostico;
    private String prescricao;
    @ManyToOne
    @JoinColumn(name="id_paciente")
    private Paciente paciente;
    @ManyToOne
    @JoinColumn(name="id_medico")
    private Medico medico;
    private Instant dataConsulta;

    @PrePersist
    public void prePersist() {
        dataConsulta = Instant.now();
    }

    public Consulta(ConsultaDTO consultaDTO) {
        this.diagnostico = consultaDTO.getDiagnostico();
        this.prescricao = consultaDTO.getPrescricao();
//        this.paciente = new Paciente(consultaDTO.getPacienteDTO());
        this.medico = new Medico(consultaDTO.getMedicoDTO());
    }
}
