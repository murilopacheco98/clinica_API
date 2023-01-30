package com.growdev.ecommerce.dto.user.user;

import com.growdev.ecommerce.dto.user.UserDTO;
import com.growdev.ecommerce.entities.user.Paciente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PacienteDTO {
    private Long id;
    @NotBlank(message = "Campo Obrigatório")
    private String nome;
    private Date dataNascimento;
    @NotBlank(message = "Campo Obrigatório")
    private String cpf;
    private String telefone;

//    private Set<AgendamentoDTO> agendamentoDTOs = new HashSet<>();
//    private Set<ConsultaDTO> consultaDTOs = new HashSet<>();
    private UserDTO userDTO;
    public PacienteDTO(Paciente paciente) {
        this.id = paciente.getId();
        this.nome = paciente.getNome();
        this.dataNascimento = paciente.getDataNascimento();
        this.cpf = paciente.getCpf();
//        paciente.getConsultas().forEach(consulta -> this.consultaDTOs.add(new ConsultaDTO(consulta)));
//        paciente.getAgendamentos().forEach(agendamento -> this.agendamentoDTOs.add(new AgendamentoDTO(agendamento)));
        this.userDTO = new UserDTO(paciente.getUsuario());
    }
}
