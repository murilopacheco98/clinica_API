package com.growdev.ecommerce.dto.auxiliar;

import lombok.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class DisponibilidadeDate {
    private Long id;
    private List<MedicoHour> medicoHour = new ArrayList<>();
    private List<LocalTime> inicio = new ArrayList<>();
    private String monthName;
    private String monthNumber;
    private String dayOfWeek;
    private String day;
    private Integer year;
}
