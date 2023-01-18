package com.growdev.ecommerce.services;

import com.growdev.ecommerce.dto.AgendamentoDTO;
import com.growdev.ecommerce.dto.DisponibilidadeResponse;
import com.growdev.ecommerce.dto.HorarioCompromisso;
import com.growdev.ecommerce.dto.user.medico.MedicoDTO;
import com.growdev.ecommerce.entities.Agendamento;
import com.growdev.ecommerce.entities.Especialidade;
import com.growdev.ecommerce.entities.user.Medico;
import com.growdev.ecommerce.entities.user.Paciente;
import com.growdev.ecommerce.exceptions.exception.InternalServerException;
import com.growdev.ecommerce.exceptions.exception.ResourceNotFoundException;
import com.growdev.ecommerce.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class AgendamentoService {
    @Autowired
    private AgendamentoRepository agendamentoRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    @Transactional(readOnly = true)
    public List<AgendamentoDTO> findAll() {
        List<Agendamento> agendamentoList = agendamentoRepository.findAll();
        return agendamentoList.stream().map(AgendamentoDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AgendamentoDTO findById(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("NOT FOUND " + id));
        return new AgendamentoDTO(agendamento);
    }


    public void delete(Long id) {
        try {
            agendamentoRepository.deleteById(id);
        }//é do spring e serve para reclamar que nao executou nada no banco
        //na reclamação(exceção) devemos instancia/propagar uma excecao personalizada
        catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Not found " + id);
        }//caso a categoria tenha dados vinculados a ela, não poderá ser excluida, nisso, apresentamos
        //um erro de violação dos dados integrados.
        catch (DataIntegrityViolationException e) {
            throw new InternalServerException("Intregrity Violation");
        }
    }

    public AgendamentoDTO create(AgendamentoDTO agendamentoDTO) {
        Agendamento agendamento = new Agendamento();

        agendamento.setTitulo(agendamentoDTO.getTitulo());
        agendamento.setInicio(agendamentoDTO.getInicio());
        agendamento.setFim(agendamentoDTO.getFim());

        Especialidade especialidade = especialidadeRepository.findByNome(agendamentoDTO.getEspecialidade().getNome());
        if (especialidade == null) throw new ResourceNotFoundException("Especialidade não encontrada.");
        agendamento.setEspecialidade(especialidade);

        agendamento.setDataConsulta(agendamentoDTO.getDataConsulta());
        Medico medico = medicoRepository.findByNomeJaleco(agendamentoDTO.getMedicoDTO().getNomeJaleco());
        if (medico == null) throw new ResourceNotFoundException("Medico não encontrada.");
        agendamento.setMedico(medico);

        Paciente paciente = pacienteRepository.findByCpf(agendamentoDTO.getPacienteDTO().getCpf());
        if (paciente == null) throw new ResourceNotFoundException("CPF não encontrado.");
        agendamento.setPaciente(paciente);
        try {
            agendamentoRepository.save(agendamento);
        } catch (Exception e) {
            throw new InternalServerException("Não foi possível fazer o agendamento.");
        }
        return new AgendamentoDTO(agendamento);
    }

    public AgendamentoDTO update(AgendamentoDTO agendamentoDTO, Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado."));
//    Horario horario = horarioRepository.findByHoraMinuto(agendamentoDTO.getHorario().getHoraMinuto());
//    if (horario == null) throw new ResourceNotFoundException("Horario não encontrado.");
//    agendamento.setHorario(horario);

        Especialidade especialidade = especialidadeRepository.findByNome(agendamentoDTO.getEspecialidade().getNome());
        if (especialidade == null) throw new ResourceNotFoundException("Horario não encontrado.");
        agendamento.setEspecialidade(especialidade);

        agendamento.setDataConsulta(agendamentoDTO.getDataConsulta());
        Medico medico = medicoRepository.findByNomeJaleco(agendamentoDTO.getMedicoDTO().getNomeJaleco());
        if (medico == null) throw new ResourceNotFoundException("Horario não encontrado.");
        agendamento.setMedico(medico);

        Paciente paciente = pacienteRepository.findByCpf(agendamentoDTO.getPacienteDTO().getCpf());
        if (paciente == null) throw new ResourceNotFoundException("Horario não encontrado.");
        agendamento.setPaciente(paciente);
        try {
            agendamentoRepository.save(agendamento);
        } catch (Exception e) {
            throw new InternalServerException("Não foi possível fazer o agendamento.");
        }
        return new AgendamentoDTO(agendamento);
    }

    @Transactional(readOnly = true)
    public Page<AgendamentoDTO> findAllPaged(PageRequest pageRequest) {
        Page<Agendamento> agendamentoPage = agendamentoRepository.findAll(pageRequest);
        return agendamentoPage.map(AgendamentoDTO::new);
    }

    public List<DisponibilidadeResponse> checkAvailability(String especialidade) {
        List<String> date = List.of(LocalDate.now().toString().split("-"));
        int yearNow = Integer.parseInt(date.get(0));
        String monthNow = date.get(1);
        String dayNow = date.get(2);

        Integer lastDay = 28;
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

        for (Medico medico : medicos) {
            for (Especialidade especialidadeFound : medico.getEspecialidade()) {
                if (Objects.equals(especialidadeFound.getNome(), especialidade)) medicosDesejados.add(medico);
            }
        }
        List<DisponibilidadeResponse> availableDate = new ArrayList<>();

//        if (limiteDiaPorMedico.size() < 3) {
        for (String day : days) {
            List<DisponibilidadeResponse> limiteDiaPorMedico = new ArrayList<>();
            Collections.shuffle(hours);
            if (Integer.parseInt(day) <= lastDay) {
                if (Integer.parseInt(day) > Integer.parseInt(dayNow)) {
                    for (String hour : hours) {
                        List<String> hourFormatated = List.of(hour.toString().split(":"));
                        LocalDateTime localDateTimeInicio = LocalDateTime.of(
                                yearNow, Integer.parseInt(monthNow), Integer.parseInt(day),
                                Integer.parseInt(hourFormatated.get(0)), Integer.parseInt(hourFormatated.get(1)));
                        LocalDateTime localDateTimeFim = localDateTimeInicio.plusMinutes(30);
                        for (Medico medico : medicosDesejados) {
                            List<HorarioCompromisso> listAgendamentos = new ArrayList<>();
                            for (Agendamento agendamento : medico.getAgendamentos()) {
                                HorarioCompromisso horarioCompromisso = new HorarioCompromisso(agendamento.getInicio(), agendamento.getFim());
                                listAgendamentos.add(horarioCompromisso);
                            }
                            List<HorarioCompromisso> horarioOcupado = new ArrayList<>();
                            for (HorarioCompromisso horarioCompromisso : listAgendamentos) {
                                if (horarioCompromisso.getInicio().isBefore(localDateTimeInicio) & horarioCompromisso.getFim().isAfter(localDateTimeInicio)) {
                                    horarioOcupado.add(horarioCompromisso);
                                } else if (horarioCompromisso.getInicio().isAfter(localDateTimeInicio) & horarioCompromisso.getFim().isBefore(localDateTimeFim)) {
                                    horarioOcupado.add(horarioCompromisso);
                                } else if (horarioCompromisso.getInicio().isBefore(localDateTimeFim) & horarioCompromisso.getFim().isAfter(localDateTimeFim)) {
                                    horarioOcupado.add(horarioCompromisso);
                                }
                            }

                            if (horarioOcupado.size() == 0) {
                                DisponibilidadeResponse disponibilidadeResponse = new DisponibilidadeResponse(localDateTimeInicio, new MedicoDTO(medico));
                                limiteDiaPorMedico.add(disponibilidadeResponse);
                                if (limiteDiaPorMedico.size() < 6) availableDate.add(disponibilidadeResponse);
                            }
                        }
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
            if (Integer.parseInt(day) < lastDay) {
//                if (Integer.parseInt(day) > Integer.parseInt(dayNow)) {
                for (String hour : hours) {
                    List<String> hourFormatated = List.of(hour.toString().split(":"));
                    LocalDateTime localDateTimeInicio = LocalDateTime.of(
                            yearNow, Integer.parseInt(monthNow), Integer.parseInt(day),
                            Integer.parseInt(hourFormatated.get(0)), Integer.parseInt(hourFormatated.get(1)));
                    LocalDateTime localDateTimeFim = localDateTimeInicio.plusMinutes(30);
                    for (Medico medico : medicosDesejados) {
                        List<HorarioCompromisso> listAgendamentos = new ArrayList<>();
                        for (Agendamento agendamento : medico.getAgendamentos()) {
                            HorarioCompromisso horarioCompromisso = new HorarioCompromisso(agendamento.getInicio(), agendamento.getFim());
                            listAgendamentos.add(horarioCompromisso);
                        }
                        List<HorarioCompromisso> horarioOcupado = new ArrayList<>();
                        for (HorarioCompromisso horarioCompromisso : listAgendamentos) {
                            if (horarioCompromisso.getInicio().isBefore(localDateTimeInicio) & horarioCompromisso.getFim().isAfter(localDateTimeInicio)) {
                                horarioOcupado.add(horarioCompromisso);
                            } else if (horarioCompromisso.getInicio().isAfter(localDateTimeInicio) & horarioCompromisso.getFim().isBefore(localDateTimeFim)) {
                                horarioOcupado.add(horarioCompromisso);
                            } else if (horarioCompromisso.getInicio().isBefore(localDateTimeFim) & horarioCompromisso.getFim().isAfter(localDateTimeFim)) {
                                horarioOcupado.add(horarioCompromisso);
                            }
                        }
                        if (horarioOcupado.size() >= 1) {
                            DisponibilidadeResponse disponibilidadeResponse = new DisponibilidadeResponse(localDateTimeInicio, new MedicoDTO(medico));
                            availableDate.add(disponibilidadeResponse);
                        }
                    }
//                    }
                }
            }
        }
        return availableDate;
    }
}
