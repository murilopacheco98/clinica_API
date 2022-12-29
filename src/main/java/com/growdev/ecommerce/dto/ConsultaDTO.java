package com.growdev.ecommerce.dto;

import com.growdev.ecommerce.dto.user.medico.MedicoDTO;
import com.growdev.ecommerce.dto.user.user.PacienteDTO;
import com.growdev.ecommerce.entities.Consulta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConsultaDTO {
  private Long id;
  @NotBlank(message = "O diagnostico é obrigatória.")
  private String diagnostico;
  @NotBlank(message = "A prescrição médica é obrigatória.")
  private String prescricao;
  private PacienteDTO pacienteDTO;
  private MedicoDTO medicoDTO;
  private Instant dataConsulta;

  public ConsultaDTO(Consulta consulta) {
    this.id = consulta.getId();
    this.diagnostico = consulta.getDiagnostico();
    this.prescricao = consulta.getPrescricao();
    this.pacienteDTO = new PacienteDTO(consulta.getPaciente());
    this.medicoDTO = new MedicoDTO(consulta.getMedico());
    this.dataConsulta = consulta.getDataConsulta();
  }
}
