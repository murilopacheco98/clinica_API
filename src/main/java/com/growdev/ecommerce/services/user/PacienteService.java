package com.growdev.ecommerce.services.user;

import com.growdev.ecommerce.dto.user.user.PacienteDTO;
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
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.NotAcceptableStatusException;

//implements UserDetailsService
@Service
@Transactional
public class PacienteService { //foi implementado porque é ele que retorna
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Page<PacienteDTO> findAllPageable(Pageable pageable) {
        Page<Paciente> pacientes = pacienteRepository.findAll(pageable);
        return pacientes.map(PacienteDTO::new);
    }

    @Transactional(readOnly = true)
    public PacienteDTO findByEmail(String email) {
        Paciente usuario = pacienteRepository.findByEmail(email);
        return new PacienteDTO(usuario);
    }

    public PacienteDTO create(PacienteDTO pacienteDTO, UserEntity userEntity) {
        Paciente paciente = new Paciente();
        paciente.setNome(pacienteDTO.getNome());
        paciente.setDataNascimento(pacienteDTO.getDataNascimento());

        Paciente pacienteFound = pacienteRepository.findByCpf(pacienteDTO.getCpf());
        if (pacienteFound != null) throw new BadRequestException("Este CPF já está cadastrado.");

        paciente.setCpf(pacienteDTO.getCpf());

        try {
            userRepository.save(userEntity);
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível salvar o usuário");
        }
        UserEntity userFound = userRepository.findByEmail(userEntity.getEmail());

        if (userFound == null) throw new ResourceNotFoundException("Este usuário não existe.");

        paciente.setUsuario(userFound);

        try {
            pacienteRepository.save(paciente);
        } catch (Exception e) {
            throw new InternalServerException("Não foi possível salvar este paciente.");
        }
        return new PacienteDTO(paciente);
    }

    public PacienteDTO update(PacienteDTO pacienteDTO, Long id) {
        Paciente paciente = pacienteRepository.findById(id).orElseThrow(() -> new NotAcceptableStatusException("Usuário não encontrado."));
        paciente.setNome(pacienteDTO.getNome());
        paciente.setDataNascimento(pacienteDTO.getDataNascimento());
        paciente.setCpf(pacienteDTO.getCpf());

//        for (AgendamentoDTO agendamentoDTO : pacienteDTO.getAgendamentoDTOs()) {
//            Agendamento agendamento = new Agendamento(agendamentoDTO);
//            paciente.getAgendamentos().add(agendamento);
//        }
//        for (ConsultaDTO consultaDTO : pacienteDTO.getConsultaDTOs()) {
//            Consulta consulta = new Consulta(consultaDTO);
//            paciente.getConsultas().add(consulta);
//        }
        pacienteRepository.save(paciente);
        return new PacienteDTO(paciente);
    }

    public void delete(Long id) {
        try {
            pacienteRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new InternalServerException("Intregrity violation");
        }
    }
}
