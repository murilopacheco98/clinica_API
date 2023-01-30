package com.growdev.ecommerce.dto.auxiliar;

import com.growdev.ecommerce.dto.user.medico.MedicoDTO;
import lombok.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class MedicoHour {
    private MedicoDTO medicoDTO;
    private List<LocalTime> inicio = new ArrayList<>();
}
