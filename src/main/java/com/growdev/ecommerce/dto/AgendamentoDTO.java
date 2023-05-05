package com.growdev.ecommerce.dto;

import com.growdev.ecommerce.dto.user.medico.MedicoDTO;
import com.growdev.ecommerce.dto.user.user.PacienteDTO;
import com.growdev.ecommerce.entities.*;
import com.growdev.ecommerce.entities.Especialidade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AgendamentoDTO {
    private Long id;
    private Especialidade especialidade;
    private MedicoDTO medicoDTO;
    private PacienteDTO pacienteDTO;
    private String titulo;
    @NotNull
    private LocalTime inicio;
    @NotNull
    private LocalTime fim;
    private LocalDate dataConsulta;

    public AgendamentoDTO(Agendamento agendamento) {
        this.id = agendamento.getId();
        this.especialidade = agendamento.getEspecialidade();
        this.medicoDTO = new MedicoDTO(agendamento.getMedico());
        if (agendamento.getPaciente() != null) {
            this.pacienteDTO = new PacienteDTO(agendamento.getPaciente());
        }
        this.titulo = agendamento.getTitulo();
        this.inicio = agendamento.getInicio();
        this.fim = agendamento.getFim();
        this.dataConsulta = agendamento.getDataConsulta();
    }
}
