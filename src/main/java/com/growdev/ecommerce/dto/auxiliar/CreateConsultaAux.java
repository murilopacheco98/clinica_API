package com.growdev.ecommerce.dto.auxiliar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateConsultaAux {
    private String diagnostico;
    private String prescricao;
    private String especialidadeName;
    private String pacienteEmail;
    private String medicoCrm;
}
