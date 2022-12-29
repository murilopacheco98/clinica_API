package com.growdev.ecommerce.services;

import com.growdev.ecommerce.entities.Authority;
import com.growdev.ecommerce.exceptions.exception.BadRequestException;
import com.growdev.ecommerce.exceptions.exception.InternalServerException;
import com.growdev.ecommerce.exceptions.exception.ResourceNotFoundException;
import com.growdev.ecommerce.repositories.AuthorityRepository;
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
public class AuthorityService {

    @Autowired
    AuthorityRepository authorityRepository;

    @Transactional(readOnly = true)
    public Page<Authority> findAllPaged(PageRequest pageRequest) {
        return authorityRepository.findAll(pageRequest);
    }

    @Transactional(readOnly = true)
    public List<Authority> findAll() {
        return authorityRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Authority findById(Long id) {
        return authorityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("NOT FOUND " + id));
    }

    public void delete(Long id) {
        try {
            authorityRepository.deleteById(id);
        }//é do spring e serve para reclamar que nao executou nada no banco
        catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Not found " + id);
        }//caso a categoria tenha dados vinculados a ela, não poderá ser excluida, nisso, apresentamos
        catch (DataIntegrityViolationException e) {
            throw new InternalServerException("Intregrity Violation");
        }
    }

    public Authority create(Authority authority) {
        try {
            authorityRepository.save(authority);
        } catch (Exception e) {
            throw new BadRequestException("Can't save this city.");
        }
        return authority;
    }

    public Authority update(Authority authorityUpdated, Long id) {
        Authority authority = authorityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("NOT FOUND " + id));
        try {
            authority.setAuthority(authorityUpdated.getAuthority());
            authorityRepository.save(authority);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Id não encontrado: " + id);
        }
        return authority;
    }
}
