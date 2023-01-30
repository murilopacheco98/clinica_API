package com.growdev.ecommerce.services;

import com.growdev.ecommerce.dto.ConsultaDTO;
import com.growdev.ecommerce.dto.auxiliar.CreateConsultaAux;
import com.growdev.ecommerce.entities.Consulta;
import com.growdev.ecommerce.entities.user.Medico;
import com.growdev.ecommerce.entities.user.Paciente;
import com.growdev.ecommerce.exceptions.exception.BadRequestException;
import com.growdev.ecommerce.exceptions.exception.ResourceNotFoundException;
import com.growdev.ecommerce.repositories.ConsultaRepository;
import com.growdev.ecommerce.repositories.MedicoRepository;
import com.growdev.ecommerce.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ConsultaService {
  @Autowired
  private ConsultaRepository consultaRepository;
  @Autowired
  private PacienteRepository pacienteRepository;
  @Autowired
  private MedicoRepository medicoRepository;

  @Transactional(readOnly = true)
  public Page<ConsultaDTO> findAll(Pageable pageable) {//pageRequest
    Page<Consulta> list = consultaRepository.findAll(pageable);
    return list.map(ConsultaDTO::new);
  }

  @Transactional(readOnly = true)
  public Page<ConsultaDTO> findAllByPacienteId(Long id, Pageable pageable) {
    Page<Consulta> consultaPage = consultaRepository.findByIdPacienteId(id, pageable);
    return consultaPage.map(ConsultaDTO::new);
  }

  @Transactional(readOnly = true)
  public Page<ConsultaDTO> findAllByMedicoId(Long id, Pageable pageable) {
    Page<Consulta> consultaPage = consultaRepository.findByIdMedicoId(id, pageable);
    return consultaPage.map(ConsultaDTO::new);
  }

  public ConsultaDTO create(CreateConsultaAux createConsultaAux) {
    Consulta consulta = new Consulta();
    consulta.setDiagnostico(createConsultaAux.getDiagnostico());
    consulta.setPrescricao(createConsultaAux.getPrescricao());

    Paciente paciente = pacienteRepository.findByEmail(createConsultaAux.getPacienteEmail());
    if (paciente == null) throw new BadRequestException("Não foi possível encontrar este paciente");
    consulta.setPaciente(paciente);

    Medico medico = medicoRepository.findByCrm(createConsultaAux.getMedicoCrm());
    if (medico == null) throw new BadRequestException("Não foi possível encontrar este médico.");
    consulta.setMedico(medico);

    try {
      consulta = consultaRepository.save(consulta);
    } catch (Exception e) {
      throw new BadRequestException("Não foi possível criar está consulta.");
    }
    return new ConsultaDTO(consulta);
  }

  public ConsultaDTO update(ConsultaDTO consultaDTO, Long id) {
    Consulta consulta = consultaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("NOT FOUND " + id));
    consulta.setDiagnostico(consulta.getDiagnostico());
    consulta.setPrescricao(consultaDTO.getPrescricao());
    Paciente paciente = pacienteRepository.findByCpf(consultaDTO.getPacienteDTO().getCpf());
    if (paciente == null) {
      throw new BadRequestException("Não foi possível encontrar o paciente");
    }
    consulta.setPaciente(paciente);
    Medico medico = medicoRepository.findByNomeJaleco(consultaDTO.getMedicoDTO().getNomeJaleco());
    consulta.setMedico(medico);
    try {
      consultaRepository.save(consulta);
    } catch (Exception e) {
      throw new BadRequestException("Can't save this consulta.");
    }
    return new ConsultaDTO(consulta);
  }

  public void delete(Long id) {
    try {
      consultaRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new ResourceNotFoundException("Id not found " + id);
    } catch (DataIntegrityViolationException e) {
    throw new BadRequestException("Intregrity violation");
    }
  }
}
