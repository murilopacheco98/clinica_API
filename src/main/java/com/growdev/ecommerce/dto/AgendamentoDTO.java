package com.growdev.ecommerce.dto;

import com.growdev.ecommerce.dto.user.medico.MedicoDTO;
import com.growdev.ecommerce.dto.user.user.PacienteDTO;
import com.growdev.ecommerce.entities.*;
import com.growdev.ecommerce.entities.Especialidade;
import com.growdev.ecommerce.entities.user.Medico;
import com.growdev.ecommerce.entities.user.Paciente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AgendamentoDTO {
    private Long id;
    @NotBlank(message = "A especialidade é obrigatória")
    @Size(min = 2, max = 50, message = "Deve ter entre 2 a 50 caracteres")
    private Especialidade especialidade;
    private MedicoDTO medicoDTO;
    private PacienteDTO pacienteDTO;
    private String titulo;

    @NotNull
    private LocalDateTime inicio;

    @NotNull
    private LocalDateTime fim;
    private LocalDate dataConsulta;

    public AgendamentoDTO(Agendamento agendamento) {
        this.id = agendamento.getId();
        this.especialidade = agendamento.getEspecialidade();
        this.medicoDTO = new MedicoDTO(agendamento.getMedico());
        this.pacienteDTO = new PacienteDTO(agendamento.getPaciente());
        this.titulo = agendamento.getTitulo();
        this.inicio = agendamento.getInicio();
        this.fim = agendamento.getFim();
        this.dataConsulta = agendamento.getDataConsulta();
    }
}
