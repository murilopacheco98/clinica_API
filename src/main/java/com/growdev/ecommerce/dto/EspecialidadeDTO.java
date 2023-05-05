package com.growdev.ecommerce.dto;

import com.growdev.ecommerce.entities.Especialidade;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EspecialidadeDTO implements Serializable {
    private Long id;
    @NotBlank(message = "O nome da especilidade é obrigatório.")
    private String nome;
//    private String descricao;
//    private Set<MedicoDTO> medicoDTO = new HashSet<>();

    public EspecialidadeDTO(Especialidade especialidade) {
        id = especialidade.getId();
        nome = especialidade.getNome();
//        descricao = especialidade.getDescricao();
//        especialidade.getMedicos().forEach(medico -> this.medicoDTO.add(new MedicoDTO(medico)));
    }
}
