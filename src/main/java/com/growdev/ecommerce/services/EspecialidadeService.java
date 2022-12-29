package com.growdev.ecommerce.services;

import com.growdev.ecommerce.entities.Especialidade;
import com.growdev.ecommerce.exceptions.exception.BadRequestException;
import com.growdev.ecommerce.exceptions.exception.InternalServerException;
import com.growdev.ecommerce.exceptions.exception.ResourceNotFoundException;
import com.growdev.ecommerce.repositories.EspecialidadeRepository;
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
public class EspecialidadeService {
    @Autowired
    EspecialidadeRepository especialidadeRepository;

    @Transactional(readOnly = true)
    public List<Especialidade> findAll() {
        return especialidadeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Especialidade findById(Long id) {
        return especialidadeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("NOT FOUND " + id));
    }

    public void delete(Long id) {
        try {
            especialidadeRepository.deleteById(id);
        }//é do spring e serve para reclamar que nao executou nada no banco
        catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Not found " + id);
        }//caso a categoria tenha dados vinculados a ela, não poderá ser excluida, nisso, apresentamos
        catch (DataIntegrityViolationException e) {
            throw new InternalServerException("Intregrity Violation");
        }
    }

    public Especialidade create(Especialidade especialidade) {
        try {
            especialidadeRepository.save(especialidade);
        } catch (Exception e) {
            throw new BadRequestException("Can't save this city.");
        }
        return especialidade;
    }

    public Especialidade update(Especialidade especialidadeUpdated, Long id) {
        Especialidade especialidadeBD = especialidadeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("NOT FOUND " + id));
        try {
            especialidadeBD.setNome(especialidadeUpdated.getNome());
            especialidadeBD.setDescricao(especialidadeUpdated.getDescricao());
            especialidadeRepository.save(especialidadeBD);
        } catch (ResourceNotFoundException e) {
            throw new BadRequestException("Os dados da requisição estão errados.");
        }
        return especialidadeBD;
    }

    @Transactional(readOnly = true)
    public Page<Especialidade> findAllPaged(PageRequest pageRequest) {
        return especialidadeRepository.findAll(pageRequest);
    }
}
