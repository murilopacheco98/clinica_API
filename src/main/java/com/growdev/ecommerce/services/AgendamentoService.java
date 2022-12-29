package com.growdev.ecommerce.services;

import com.growdev.ecommerce.dto.AgendamentoDTO;
import com.growdev.ecommerce.entities.Agendamento;
import com.growdev.ecommerce.entities.Especialidade;
import com.growdev.ecommerce.entities.Horario;
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

import java.util.List;
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
  private HorarioRepository horarioRepository;
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
    Horario horario = horarioRepository.findByHoraMinuto(agendamentoDTO.getHorario().getHoraMinuto());
    if (horario == null) throw new ResourceNotFoundException("Horario não encontrado.");
    agendamento.setHorario(horario);

    Especialidade especialidade = especialidadeRepository.findByNome(agendamentoDTO.getEspecialidade().getNome());
    if (especialidade == null) throw new ResourceNotFoundException("Horario não encontrado.");
    agendamento.setEspecialidade(especialidade);

    agendamento.setDataConsulta(agendamentoDTO.getDataConsulta());
    Medico medico = medicoRepository.findByNomeJaleco(agendamentoDTO.getMedico().getNomeJaleco());
    if (medico == null) throw new ResourceNotFoundException("Horario não encontrado.");
    agendamento.setMedico(medico);

    Paciente paciente = pacienteRepository.findByCpf(agendamentoDTO.getPaciente().getCpf());
    if (paciente == null) throw new ResourceNotFoundException("Horario não encontrado.");
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
    Horario horario = horarioRepository.findByHoraMinuto(agendamentoDTO.getHorario().getHoraMinuto());
    if (horario == null) throw new ResourceNotFoundException("Horario não encontrado.");
    agendamento.setHorario(horario);

    Especialidade especialidade = especialidadeRepository.findByNome(agendamentoDTO.getEspecialidade().getNome());
    if (especialidade == null) throw new ResourceNotFoundException("Horario não encontrado.");
    agendamento.setEspecialidade(especialidade);

    agendamento.setDataConsulta(agendamentoDTO.getDataConsulta());
    Medico medico = medicoRepository.findByNomeJaleco(agendamentoDTO.getMedico().getNomeJaleco());
    if (medico == null) throw new ResourceNotFoundException("Horario não encontrado.");
    agendamento.setMedico(medico);

    Paciente paciente = pacienteRepository.findByCpf(agendamentoDTO.getPaciente().getCpf());
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
}
