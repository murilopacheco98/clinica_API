package com.growdev.ecommerce.dto.auxiliar;

import com.growdev.ecommerce.dto.user.medico.MedicoDTO;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

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
