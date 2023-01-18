package com.growdev.ecommerce.dto;

import com.growdev.ecommerce.dto.user.medico.MedicoDTO;
import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class DisponibilidadeResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime inicio;
    private MedicoDTO medicoDTO;

    public DisponibilidadeResponse(LocalDateTime localDateTimeFormatated, MedicoDTO medicoDTO) {
        this.inicio = localDateTimeFormatated;
        this.medicoDTO = medicoDTO;
    }
}
