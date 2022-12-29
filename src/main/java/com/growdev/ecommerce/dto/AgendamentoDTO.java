package com.growdev.ecommerce.dto;

import com.growdev.ecommerce.entities.*;
import com.growdev.ecommerce.entities.Especialidade;
import com.growdev.ecommerce.entities.user.Medico;
import com.growdev.ecommerce.entities.user.Paciente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AgendamentoDTO {
  private Long id;
  @NotBlank(message = "A especialidade é obrigatória")
  @Size(min = 2, max = 50, message = "Deve ter entre 2 a 50 caracteres")
  private Especialidade especialidade;
  private Medico medico;
  private Paciente paciente;
  private Horario horario;
  private LocalDate dataConsulta;

  public AgendamentoDTO(Agendamento agendamento) {
    this.id = agendamento.getId();
    this.especialidade = agendamento.getEspecialidade();
    this.medico = agendamento.getMedico();
    this.paciente = agendamento.getPaciente();
    this.horario = agendamento.getHorario();
    this.dataConsulta = agendamento.getDataConsulta();
  }
}
