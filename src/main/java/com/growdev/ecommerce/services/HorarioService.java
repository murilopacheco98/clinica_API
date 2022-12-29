package com.growdev.ecommerce.services;

import com.growdev.ecommerce.entities.Horario;
import com.growdev.ecommerce.exceptions.exception.BadRequestException;
import com.growdev.ecommerce.exceptions.exception.InternalServerException;
import com.growdev.ecommerce.exceptions.exception.ResourceNotFoundException;
import com.growdev.ecommerce.repositories.HorarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HorarioService {
    @Autowired
    HorarioRepository horarioRepository;

    @Transactional(readOnly = true)
    public List<Horario> findAll() {
        return horarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<Horario> findAllPaged(PageRequest pageRequest) {
        return horarioRepository.findAll(pageRequest);
    }

    @Transactional(readOnly = true)
    public Horario findById(Long id) {
        return horarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("NOT FOUND " + id));
    }

    public void delete(Long id) {
        try {
            horarioRepository.deleteById(id);
        }//é do spring e serve para reclamar que nao executou nada no banco
        catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Not found " + id);
        }//caso a categoria tenha dados vinculados a ela, não poderá ser excluida, nisso, apresentamos
        catch (DataIntegrityViolationException e) {
            throw new InternalServerException("Intregrity Violation");
        }
    }

    public Horario create(Horario horario) {
        try {
            horarioRepository.save(horario);
        } catch (Exception e) {
            throw new BadRequestException("Não é possível salvar esse horario.");
        }
        return horario;
    }

    public Horario update(Horario horario, Long id) {
        Horario horarioFound = horarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("NOT FOUND " + id));
        try {
            horarioFound.setHoraMinuto(horario.getHoraMinuto());
            horarioRepository.save(horarioFound);
        } catch (ResourceNotFoundException e) {
            throw new BadRequestException("Os dados da requisição estão errados.");
        }
        return horarioFound;
    }
}
