package com.growdev.ecommerce.services;

import com.growdev.ecommerce.dto.auxiliar.DisponibilidadeDate;
import com.growdev.ecommerce.dto.auxiliar.HorarioCompromisso;
import com.growdev.ecommerce.dto.auxiliar.MedicoHour;
import com.growdev.ecommerce.dto.user.medico.MedicoDTO;
import com.growdev.ecommerce.entities.Agendamento;
import com.growdev.ecommerce.entities.Especialidade;
import com.growdev.ecommerce.entities.user.Medico;
import com.growdev.ecommerce.repositories.MedicoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@SpringBootTest
class HorarioServiceTest {
    @Autowired
    AgendamentoService agendamentoService;
    @Autowired
    MedicoRepository medicoRepository;

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
        List<DisponibilidadeDate> disponibilidadeDateList = agendamentoService.checkAvailability(" Cirurgia de Epilepsia");

        Assertions.assertFalse(disponibilidadeDateList.isEmpty());
        System.out.println(disponibilidadeDateList);
    }

    @Test
    void checkAvailability() {
        String especialidade = " Cirurgia Cardiovascular";
        List<String> date = List.of(LocalDate.now().toString().split("-"));
        int yearNow = Integer.parseInt(date.get(0));
        String monthNow = date.get(1);
        String dayNow = date.get(2);

        int lastDay = 28;
        if (Objects.equals(monthNow, "02")) lastDay = 28;
        else if (Objects.equals(monthNow, "04") | Objects.equals(monthNow, "06") | Objects.equals(monthNow, "09") | Objects.equals(monthNow, "11")) {
            lastDay = 30;
        } else lastDay = 31;

        List<String> hours = new ArrayList<>(Arrays.asList("07:00:00", "07:30:00", "08:00:00",
                "08:30:00", "09:00:00", "09:30:00", "10:00:00", "10:30:00", "11:00:00",
                "13:00:00", "13:30:00", "14:00:00", "14:30:00", "15:00:00", "15:30:00",
                "16:00:00", "16:30:00", "17:00:00", "17:30:00"));
        List<String> days = new ArrayList<>(Arrays.asList("01", "02", "03", "04", "05", "06",
                "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
                "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"));
//        List<String> months = new ArrayList<>(Arrays.asList("01", "02", "03", "04", "05", "06",
//                "07", "08", "09", "10", "11", "12"));
        List<Medico> medicos = medicoRepository.findAll();
        List<Medico> medicosDesejados = new ArrayList<>();
        System.out.println(especialidade);
        for (Medico medico : medicos) {
            for (Especialidade especialidadeFound : medico.getEspecialidade()) {
                if (Objects.equals(especialidadeFound.getNome(), especialidade)) medicosDesejados.add(medico);
            }
        }
        List<DisponibilidadeDate> availableDate = new ArrayList<>();
        for (String day : days) {
            if (Integer.parseInt(day) <= lastDay) {
                DisponibilidadeDate disponibilidadeDate = new DisponibilidadeDate();
                LocalDate localDate = LocalDate.of(yearNow, Integer.parseInt(monthNow), Integer.parseInt(day));
                disponibilidadeDate.setDay(day);
                disponibilidadeDate.setDayOfWeek(formatWeekDay(String.valueOf(localDate.getDayOfWeek())));
                disponibilidadeDate.setMonthName(formatMonth(String.valueOf(localDate.getMonth())));
                Collections.shuffle(hours);
                if (Integer.parseInt(day) > Integer.parseInt(dayNow)) {
                    MedicoHour medicoHour = new MedicoHour();
                    disponibilidadeDate.getMedicoHour().add(medicoHour);
                    List<DisponibilidadeDate> limiteDiaPorMedico = new ArrayList<>();
                    for (String hour : hours) {
                        List<HorarioCompromisso> horarioOcupado = new ArrayList<>();
                        List<String> hourFormatated = List.of(hour.split(":"));
                        LocalTime localTimeInicio = LocalTime.of(
                                Integer.parseInt(hourFormatated.get(0)), Integer.parseInt(hourFormatated.get(1)));
                        LocalTime localTimeFim = localTimeInicio.plusMinutes(30);
                        for (Medico medico : medicosDesejados) {
                            disponibilidadeDate.getMedicoHour().get(disponibilidadeDate.getMedicoHour().size() - 1).setMedicoDTO(new MedicoDTO(medico));
                            List<HorarioCompromisso> listAgendamentos = new ArrayList<>();
                            for (Agendamento agendamento : medico.getAgendamentos()) {
                                HorarioCompromisso horarioCompromisso = new HorarioCompromisso(agendamento.getInicio(), agendamento.getFim());
                                listAgendamentos.add(horarioCompromisso);
                            }
                            for (HorarioCompromisso horarioCompromisso : listAgendamentos) {
                                if (horarioCompromisso.getInicio().isBefore(localTimeInicio) & horarioCompromisso.getFim().isAfter(localTimeInicio)) {
                                    horarioOcupado.add(horarioCompromisso);
                                } else if (horarioCompromisso.getInicio().isAfter(localTimeInicio) & horarioCompromisso.getFim().isBefore(localTimeFim)) {
                                    horarioOcupado.add(horarioCompromisso);
                                } else if (horarioCompromisso.getInicio().isBefore(localTimeFim) & horarioCompromisso.getFim().isAfter(localTimeFim)) {
                                    horarioOcupado.add(horarioCompromisso);
                                }
                            }
                            if (horarioOcupado.size() == 0) {
                                limiteDiaPorMedico.add(disponibilidadeDate);
                                if (limiteDiaPorMedico.size() < 6) {
                                    disponibilidadeDate.getMedicoHour().get(disponibilidadeDate.getMedicoHour().size() - 1).getInicio().add(LocalTime.of(Integer.parseInt(hourFormatated.get(0)), Integer.parseInt(hourFormatated.get(1))));
                                    disponibilidadeDate.getInicio().add(LocalTime.of(Integer.parseInt(hourFormatated.get(0)), Integer.parseInt(hourFormatated.get(1))));
                                }
                            }
                        }
                    }
                    disponibilidadeDate.setId((long) (availableDate.size() + 1));
                    if (!Objects.equals(disponibilidadeDate.getDayOfWeek(), "SÁBADO") & !Objects.equals(disponibilidadeDate.getDayOfWeek(), "DOMINGO")) {
                        availableDate.add(disponibilidadeDate);
                    }
                }
            }
        }
        if ((Integer.parseInt(monthNow) + 1) == 13) {
            monthNow = "01";
            yearNow = yearNow + 1;
        } else {
            monthNow = String.valueOf(Integer.parseInt(monthNow) + 1);
            if (Integer.parseInt(monthNow) < 10) monthNow = "0" + monthNow;
        }

        if (monthNow.equals("02")) lastDay = 28;
        else if (monthNow.equals("04") | monthNow.equals("06") | monthNow.equals("09") | monthNow.equals("11"))
            lastDay = 30;
        else lastDay = 31;

        for (String day : days) {
            DisponibilidadeDate disponibilidadeDate = new DisponibilidadeDate();
            if (Integer.parseInt(day) <= lastDay) {
                LocalDate localDate = LocalDate.of(yearNow, Integer.parseInt(monthNow), Integer.parseInt(day));
                disponibilidadeDate.setDay(day);
                disponibilidadeDate.setDayOfWeek(formatWeekDay(String.valueOf(localDate.getDayOfWeek())));
                disponibilidadeDate.setMonthName(formatMonth(String.valueOf(localDate.getMonth())));
                Collections.shuffle(hours);
                MedicoHour medicoHour = new MedicoHour();
                disponibilidadeDate.getMedicoHour().add(medicoHour);
                List<DisponibilidadeDate> limiteDiaPorMedico = new ArrayList<>();
                for (String hour : hours) {
                    List<HorarioCompromisso> horarioOcupado = new ArrayList<>();
                    List<String> hourFormatated = List.of(hour.split(":"));
                    LocalTime localTimeInicio = LocalTime.of(
                            Integer.parseInt(hourFormatated.get(0)), Integer.parseInt(hourFormatated.get(1)));
                    LocalTime localDateTimeFim = localTimeInicio.plusMinutes(30);
                    for (Medico medico : medicosDesejados) {
                        disponibilidadeDate.getMedicoHour().get(disponibilidadeDate.getMedicoHour().size() - 1).setMedicoDTO(new MedicoDTO(medico));
                        List<HorarioCompromisso> listAgendamentos = new ArrayList<>();
                        for (Agendamento agendamento : medico.getAgendamentos()) {
                            HorarioCompromisso horarioCompromisso = new HorarioCompromisso(agendamento.getInicio(), agendamento.getFim());
                            listAgendamentos.add(horarioCompromisso);
                        }
                        for (HorarioCompromisso horarioCompromisso : listAgendamentos) {
                            if (horarioCompromisso.getInicio().isBefore(localTimeInicio) & horarioCompromisso.getFim().isAfter(localTimeInicio)) {
                                horarioOcupado.add(horarioCompromisso);
                            } else if (horarioCompromisso.getInicio().isAfter(localTimeInicio) & horarioCompromisso.getFim().isBefore(localDateTimeFim)) {
                                horarioOcupado.add(horarioCompromisso);
                            } else if (horarioCompromisso.getInicio().isBefore(localDateTimeFim) & horarioCompromisso.getFim().isAfter(localDateTimeFim)) {
                                horarioOcupado.add(horarioCompromisso);
                            }
                        }
                        if (horarioOcupado.size() == 0) {
                            limiteDiaPorMedico.add(disponibilidadeDate);
                            if (limiteDiaPorMedico.size() < 6) {
                                disponibilidadeDate.getMedicoHour().get(disponibilidadeDate.getMedicoHour().size() - 1).getInicio().add(LocalTime.of(Integer.parseInt(hourFormatated.get(0)), Integer.parseInt(hourFormatated.get(1))));
                                disponibilidadeDate.getInicio().add(LocalTime.of(Integer.parseInt(hourFormatated.get(0)), Integer.parseInt(hourFormatated.get(1))));
                            }
                        }
                    }
                }
                disponibilidadeDate.setId((long) (availableDate.size() + 1));
                if (!Objects.equals(disponibilidadeDate.getDayOfWeek(), "SÁBADO") & !Objects.equals(disponibilidadeDate.getDayOfWeek(), "DOMINGO")) {
                    availableDate.add(disponibilidadeDate);
                }
            }
        }
        System.out.println(availableDate);
    }

    private String formatMonth(String month) {
        if (Objects.equals(month, "JANUARY")) {
            return "JANEIRO";
        } else if (Objects.equals(month, "FEBRUARY")) {
            return "FEVEREIRO";
        } else if (Objects.equals(month, "MARCH")) {
            return "MARÇO";
        } else if (Objects.equals(month, "APRIL")) {
            return "ABRIL";
        } else if (Objects.equals(month, "MAY")) {
            return "MAIO";
        } else if (Objects.equals(month, "JUNE")) {
            return "JUNHO";
        } else if (Objects.equals(month, "JULY")) {
            return "JULHO";
        } else if (Objects.equals(month, "AUGUST")) {
            return "AGOSTO";
        } else if (Objects.equals(month, "SEPTEMBER")) {
            return "SETEMBRO";
        } else if (Objects.equals(month, "OCTOBER")) {
            return "OUTUBRO";
        } else if (Objects.equals(month, "NOVEMBER")) {
            return "NOVEMBRO";
        } else {
            return "DEZEMBRO";
        }
    }

    private String formatWeekDay(String weekDay) {
        if (Objects.equals(weekDay, "SUNDAY")) {
            return "DOMINGO";
        } else if (Objects.equals(weekDay, "MONDAY")) {
            return "SEGUNDA";
        } else if (Objects.equals(weekDay, "TUESDAY")) {
            return "TERÇA";
        } else if (Objects.equals(weekDay, "WEDNESDAY")) {
            return "QUARTA";
        } else if (Objects.equals(weekDay, "THURSDAY")) {
            return "QUINTA";
        } else if (Objects.equals(weekDay, "FRIDAY")) {
            return "SEXTA";
        } else {
            return "SÁBADO";
        }
    }
}