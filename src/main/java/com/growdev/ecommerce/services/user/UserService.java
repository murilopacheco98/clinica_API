package com.growdev.ecommerce.services.user;

import com.growdev.ecommerce.dto.user.UserDTO;
import com.growdev.ecommerce.dto.user.UserInsertDTO;
import com.growdev.ecommerce.dto.user.UserUpdateDTO;
import com.growdev.ecommerce.entities.Authority;
import com.growdev.ecommerce.entities.user.UserEntity;
import com.growdev.ecommerce.exceptions.exception.BadRequestException;
import com.growdev.ecommerce.exceptions.exception.InternalServerException;
import com.growdev.ecommerce.exceptions.exception.ResourceNotFoundException;
import com.growdev.ecommerce.repositories.AuthorityRepository;
import com.growdev.ecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private AuthorityRepository roleRepository;
  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Transactional(readOnly = true)
  public Page<UserDTO> findAllPageable(Pageable pageable) {
    Page<UserEntity> usuarios = userRepository.findAll(pageable);
    return usuarios.map(UserDTO::new);
  }

  @Transactional(readOnly = true)
  public UserDTO findById(Long id) {
    UserEntity usuario = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found " + id));
    return new UserDTO(usuario);
  }

  public UserEntity create(UserInsertDTO userInsertDTO) {
    UserEntity userEntity = new UserEntity();
    UserEntity userFound = userRepository.findByEmail(userInsertDTO.getEmail());

    if (userFound != null) throw new BadRequestException("Este email já existe");

    userEntity.setEmail(userInsertDTO.getEmail());
    userEntity.setSenha(passwordEncoder.encode(userInsertDTO.getSenha()));
    userEntity.setCodigoVerificador(userInsertDTO.getCodigoVerificador());

    if (userInsertDTO.getAuthority().isEmpty()) throw new ResourceNotFoundException("A role é obrigatória.");
    for (Authority authority : userInsertDTO.getAuthority()) {
      Authority authorityFound = roleRepository.findByAuthority(authority.getAuthority());
      if (authorityFound == null) throw new BadRequestException("Está role não existe.");
      userEntity.getAuthority().add(authorityFound);
    }

    return userEntity;
  }

  public UserDTO update(Long id, UserUpdateDTO userUpdateDTO) {
    UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found " + id));
    userEntity.setEmail(userUpdateDTO.getEmail());
    userEntity.setSenha(passwordEncoder.encode(userUpdateDTO.getSenha()));
    userEntity.setCodigoVerificador(userUpdateDTO.getCodigoVerificador());
    if (userUpdateDTO.getAuthority().isEmpty()) throw new ResourceNotFoundException("A role é obrigatória.");

    for (Authority authority : userUpdateDTO.getAuthority()) {
      Authority authorityFound = roleRepository.findById(authority.getId())
              .orElseThrow(() -> new ResourceNotFoundException("Role não encontrad"));
      userEntity.getAuthority().add(authorityFound);
    }
    try {
      userRepository.save(userEntity);
    } catch (Exception e) {
      throw new InternalServerException("Não foi possível salvar o usuário.");
    }
    return new UserDTO(userEntity);
  }

  public void delete(Long id) {
    try {
      userRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new ResourceNotFoundException("Id não encontrado: " + id);
    } catch (DataIntegrityViolationException e) {
      throw new InternalServerException("Intregrity violation");
    }
  }

// O loadUserByUsername você faz o seguinte, recebe o usuario e verifica se ele existe para ter acesso
//  @Override
//  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//    UserEntity usuario = userRepository.findByEmail(username);
//    if(usuario == null){
//      throw new UsernameNotFoundException("Email não existe");
//    }
//    return usuario;
//  }
}
