package com.growdev.ecommerce.dto.auxiliar;

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
public class AgendamentoAux {
    private String titulo;
    private String especialidadeNome;
    private String medicoCrm;
    private boolean paciente;
    private String email;
    private LocalTime inicio;
    private LocalTime fim;
    private LocalDate dataConsulta;

}
