package com.growdev.ecommerce.dto.user.medico;

import com.growdev.ecommerce.dto.EspecialidadeDTO;
import com.growdev.ecommerce.dto.user.UserDTO;
import com.growdev.ecommerce.entities.user.Medico;
import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@ToString
public class MedicoDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Campo obrigatório.")
    private String nome;
    @NotBlank(message = "Campo obrigatório.")
    private String nomeJaleco;
    @NotBlank(message = "Campo Obrigatório")
    private String crm;
    private LocalDate dataInscricao;
    private Set<EspecialidadeDTO> especialidade = new HashSet<>();
//    private Set<AgendamentoDTO> agendamentoDTOs = new HashSet<>();
//    private Set<ConsultaDTO> consultaDTOs = new HashSet<>();
    private UserDTO userDTO;

    public MedicoDTO(Medico medico) {
        this.id = medico.getId();
        this.nome = medico.getNome();
        this.crm = medico.getCrm();
        this.nomeJaleco = medico.getNomeJaleco();
        this.dataInscricao = medico.getDataInscricao();
//        this.especialidade.addAll(medico.getEspecialidade());
        medico.getEspecialidade().forEach(especialidadeFound -> especialidade.add(new EspecialidadeDTO(especialidadeFound)));
//        medico.getAgendamentos().forEach(agendamento -> agendamentoDTOs.add(new AgendamentoDTO(agendamento)));
//        medico.getConsultas().forEach(consulta -> consultaDTOs.add(new ConsultaDTO(consulta)));
        this.userDTO = new UserDTO(medico.getUserEntity());
    }
}
