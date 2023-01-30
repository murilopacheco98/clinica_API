package com.growdev.ecommerce.dto.auxiliar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
    private LocalDate dataConsulta;
}
