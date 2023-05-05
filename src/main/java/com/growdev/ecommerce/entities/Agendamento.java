package com.growdev.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.growdev.ecommerce.dto.AgendamentoDTO;
import com.growdev.ecommerce.entities.user.Medico;
import com.growdev.ecommerce.entities.user.Paciente;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "agendamento")
public class Agendamento implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_especialidade")
    private Especialidade especialidade;

    @ManyToOne
    @JoinColumn(name = "id_medico")
    private Medico medico;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;

    private String titulo;
    @Column(columnDefinition = "TIME")
    @NotNull
    private LocalTime inicio;
    @NotNull
    @Column(columnDefinition = "TIME")
    private LocalTime fim;
    @Column(name = "data_consulta")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataConsulta;
    private Instant criado;
    private Instant atualizado;

    @PrePersist
    public void prePersist() {
        criado = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        atualizado = Instant.now();
    }

    public Agendamento(AgendamentoDTO agendamentoDTO) {
        this.especialidade = agendamentoDTO.getEspecialidade();
        this.medico = new Medico(agendamentoDTO.getMedicoDTO());
        this.paciente = new Paciente(agendamentoDTO.getPacienteDTO());
        this.titulo = agendamentoDTO.getTitulo();
        this.inicio = agendamentoDTO.getInicio();
        this.fim = agendamentoDTO.getFim();
        this.dataConsulta = agendamentoDTO.getDataConsulta();
    }
}
