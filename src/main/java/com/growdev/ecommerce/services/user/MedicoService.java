package com.growdev.ecommerce.services.user;

import com.growdev.ecommerce.dto.EspecialidadeDTO;
import com.growdev.ecommerce.dto.user.medico.MedicoDTO;
import com.growdev.ecommerce.dto.user.user.PacienteDTO;
import com.growdev.ecommerce.entities.Consulta;
import com.growdev.ecommerce.entities.Especialidade;
import com.growdev.ecommerce.entities.user.Medico;
import com.growdev.ecommerce.entities.user.Paciente;
import com.growdev.ecommerce.entities.user.UserEntity;
import com.growdev.ecommerce.exceptions.exception.BadRequestException;
import com.growdev.ecommerce.exceptions.exception.InternalServerException;
import com.growdev.ecommerce.exceptions.exception.ResourceNotFoundException;
import com.growdev.ecommerce.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.NotAcceptableStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//implements UserDetailsService
@Service
@Transactional
public class MedicoService { //foi implementado porque é ele que retorna
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EspecialidadeRepository especialidadeRepository;
    @Autowired
    private AgendamentoRepository agendamentoRepository;
    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Page<MedicoDTO> findAllPaged(Pageable pageable) {
        Page<Medico> medicos = medicoRepository.findAll(pageable);
        return medicos.map(MedicoDTO::new);
    }

    @Transactional(readOnly = true)
    public MedicoDTO findByEmail(String email) {
        Medico usuario = medicoRepository.findByEmail(email);
        if (usuario == null) {
            throw new ResourceNotFoundException("Usuário não encontrado");
        }
        return new MedicoDTO(usuario);
    }

    @Transactional(readOnly = true)
    public Page<PacienteDTO> findPacientes(Long id, Pageable pageable) {
        Medico medico = medicoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Médico não encontrado."));

        List<Paciente> pacienteList = new ArrayList<>();
        for (Consulta consulta : medico.getConsultas()) {
            pacienteList.add(consulta.getPaciente());
        }

        pacienteList = pacienteList.stream().distinct().collect(Collectors.toList());
        Page<Paciente> pacientePage = new PageImpl<>(pacienteList, pageable, pacienteList.size());

        return pacientePage.map(PacienteDTO::new);
    }

    public MedicoDTO create(MedicoDTO medicoDTO, UserEntity userEntity) {
        Medico medico = new Medico();
        medico.setNome(medicoDTO.getNome());
        medico.setNomeJaleco(medicoDTO.getNomeJaleco());

        Medico medicoFound = medicoRepository.findByCrm(medicoDTO.getCrm());
        if (medicoFound != null) throw new BadRequestException("Este CRM já está cadastrado.");

        medico.setCrm(medicoDTO.getCrm());
        medico.setDataInscricao(medicoDTO.getDataInscricao());
        if (medicoDTO.getEspecialidade().isEmpty()) {
            throw new BadRequestException("A especialidade é obrigatória.");
        }
        for (EspecialidadeDTO especialidadeDTO : medicoDTO.getEspecialidade()) {
            Especialidade especialidadeFound = especialidadeRepository.findByNome(especialidadeDTO.getNome());
            if (especialidadeFound == null) throw new ResourceNotFoundException("Especialidade não encontrada.");
            medico.getEspecialidade().add(especialidadeFound);
        }
        try {
            userRepository.save(userEntity);
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível salvar o usuário");
        }
        UserEntity userFound = userRepository.findByEmail(userEntity.getEmail());

        if (userFound == null) throw new ResourceNotFoundException("Este usuário não existe.");

        medico.setUserEntity(userFound);

        try {
            medicoRepository.save(medico);
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível salvar o usuário");
        }
        return new MedicoDTO(medico);
    }

    public MedicoDTO update(MedicoDTO medicoDTO, Long id) {
        Medico medico = medicoRepository.findById(id).orElseThrow(() -> new NotAcceptableStatusException("Usuário não encontrado."));
        medico.setNome(medicoDTO.getNome());
        medico.setCrm(medicoDTO.getCrm());
        medico.setDataInscricao(medicoDTO.getDataInscricao());
        UserEntity userEntity = userRepository.findById(medicoDTO.getUserDTO().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));
        medico.setUserEntity(userEntity);
        for (EspecialidadeDTO especialidadeDTO : medicoDTO.getEspecialidade()) {
            Especialidade especialidadeFound = especialidadeRepository.findById(especialidadeDTO.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Role não encontrada."));
            medico.getEspecialidade().add(especialidadeFound);
        }
//        for (AgendamentoDTO agendamentoDTO : medicoDTO.getAgendamentoDTOs()) {
//            medico.getAgendamentos().add(new Agendamento(agendamentoDTO));
//        }
//        for (ConsultaDTO consultaDTO : medicoDTO.getConsultaDTOs()) {
//            medico.getConsultas().add(new Consulta(consultaDTO));
//        }
        medicoRepository.save(medico);
        return new MedicoDTO(medico);
    }

    public void delete(Long id) {
        try {
            medicoRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new InternalServerException("Intregrity violation");
        }
    }
}
