package com.growdev.ecommerce.services;

import com.growdev.ecommerce.dto.DisponibilidadeResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class HorarioServiceTest {

    @Autowired
    AgendamentoService agendamentoService;

    @Test
    void findAll() {
    }

    @Test
    void findAllPaged() {
    }

    @Test
    void findById() {
    }

    @Test
    void checkAvailabilityShouldReturnAvailableData() {
        List<DisponibilidadeResponse> disponibilidadeResponses = agendamentoService.checkAvailability(" Cirurgia de Epilepsia");

        Assertions.assertFalse(disponibilidadeResponses.isEmpty());
        System.out.println(disponibilidadeResponses.toString());
    }

//    @Test
//    void createShouldReturnHorarioCreated() {
//        String titulo = "teste unitário";
//        LocalDateTime inicio = LocalDateTime.now().plusDays(1).plusMonths(1).plusHours(1);
//        LocalDateTime fim = LocalDateTime.now().plusDays(1).plusMonths(1).plusHours(1);
//        LocalDateTime teste = LocalDateTime.of(2022, 01, 17, 18, 00);
//        Horario horario = new Horario(1L, titulo, inicio, fim);
//
//        System.out.println(horario.toString());
//    }

    @Test
    void createHorario() {
        List<String> date = List.of(LocalDate.now().toString().split("-"));
        String yearNow = date.get(0);
        String monthNow = date.get(1);
        String dayNow = date.get(2);

        List<String> hours = new ArrayList<>(Arrays.asList("07:00:00",
                "07:30:00", "08:00:00", "08:30:00", "09:00:00", "09:30:00",
                "10:00:00", "10:30:00", "11:00:00", "13:00:00", "13:30:00",
                "14:00:00", "14:30:00", "15:00:00", "15:30:00", "16:00:00",
                "16:30:00", "17:00:00", "17:30:00"));
        List<String> days = new ArrayList<>(Arrays.asList("01", "02", "03", "04", "05", "06",
                "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
                "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"));
//        List<String> months = new ArrayList<>(Arrays.asList("01", "02", "03", "04", "05", "06",
//                "07", "08", "09", "10", "11", "12"));

        if (monthNow == "02") {
            for (String day : days) {
                if (Integer.parseInt(day) < 29) {
                    if (Integer.parseInt(day) > Integer.parseInt(dayNow)) {
                        System.out.println(day);
//                        for (String hour : hours) {
//                        }
                    }
                }
            }
        } else if (monthNow == "04" | monthNow == "06" | monthNow == "09" | monthNow == "11") {
            for (String day : days) {
                if (Integer.parseInt(day) < 31) {
                    if (Integer.parseInt(day) > Integer.parseInt(dayNow)) {
                        System.out.println(day);
//                        for (String hour : hours) {
//                        }
                    }
                }
            }
        } else {
            for (String day : days) {
                if (Integer.parseInt(day) > Integer.parseInt(dayNow)) {
                    System.out.println(day);
                    System.out.println("Passei aqui");
//                        for (String hour : hours) {
//                        }
                }
            }
        }
        if ((monthNow + 1) == "02") {
            for (String day : days) {
                if (Integer.parseInt(day) < 29) {
                    if (Integer.parseInt(day) > Integer.parseInt(dayNow)) {
                        System.out.println(day);
//                        for (String hour : hours) {
//                        }
                    }
                }
            }
        } else if ((monthNow + 1) == "04" | (monthNow + 1) == "06" | (monthNow + 1) == "09" | (monthNow + 1) == "11") {
            for (String day : days) {
                if (Integer.parseInt(day) < 31) {
                    if (Integer.parseInt(day) > Integer.parseInt(dayNow)) {
                        System.out.println(day);
//                        for (String hour : hours) {
//                        }
                    }
                }
            }
        } else if ((monthNow + 1) == "13") {
            for (String day : days) {
                if (Integer.parseInt(day) < 31) {
                    if (Integer.parseInt(day) > Integer.parseInt(dayNow)) {
                        System.out.println(day);
//                        for (String hour : hours) {
//                        }
                    }
                }
            }
        } else {
            for (String day : days) {
                if (Integer.parseInt(day) > Integer.parseInt(dayNow)) {
                    System.out.println(day);
                    System.out.println("Passei aqui");
//                        for (String hour : hours) {
//                        }
                }
            }
        }
    }
}