package com.growdev.ecommerce.resources.user;

import com.growdev.ecommerce.dto.user.*;
import com.growdev.ecommerce.dto.user.medico.MedicoDTO;
import com.growdev.ecommerce.dto.user.medico.UserMedicoDTO;
import com.growdev.ecommerce.dto.user.user.PacienteDTO;
import com.growdev.ecommerce.dto.user.user.UserPacienteDTO;
import com.growdev.ecommerce.entities.user.UserEntity;
import com.growdev.ecommerce.repositories.UserRepository;
import com.growdev.ecommerce.services.user.MedicoService;
import com.growdev.ecommerce.services.user.PacienteService;
import com.growdev.ecommerce.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/user")
public class UserController {
  @Autowired
  UserService userService;
  @Autowired
  UserRepository userRepository;
  @Autowired
  MedicoService medicoService;
  @Autowired
  PacienteService pacienteService;

  @GetMapping("/get/pageable")
  public ResponseEntity<Page<UserDTO>> findAllUserPageable(Pageable pageable) {
    Page<UserDTO> list = userService.findAllPageable(pageable);
    return ResponseEntity.ok().body(list);
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<UserDTO> findUserById(Long id) {
    UserDTO dto = userService.findById(id);
    return ResponseEntity.ok().body(dto);
  }

  @PostMapping("/post/medico")
  public ResponseEntity<?> signUpMedico(@Valid @RequestBody UserMedicoDTO userMedicoDTO){
    UserEntity userEntity = userService.create(userMedicoDTO.getUserInsertDTO());
    MedicoDTO medicoDTO = medicoService.create(userMedicoDTO.getMedicoDTO(), userEntity);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/post/medico").buildAndExpand(userEntity.getId())
      .toUri();
    return ResponseEntity.ok().body(medicoDTO);
  }

  @PostMapping("/post/paciente")
  public ResponseEntity<PacienteDTO> signUpPaciente(@Valid @RequestBody UserPacienteDTO userPacienteDTO){
    UserEntity userEntity = userService.create(userPacienteDTO.getUserInsertDTO());
    PacienteDTO pacienteDTO = pacienteService.create(userPacienteDTO.getPacienteDTO(), userEntity.getEmail());
    URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/post/paciente").buildAndExpand(userEntity.getId())
            .toUri();
    return ResponseEntity.ok().body(pacienteDTO);
  }

  @PutMapping("/put/{id}")
  public ResponseEntity<UserDTO> update(@Valid @RequestBody UserUpdateDTO userUpdateDTO, @PathVariable Long id){
    UserDTO updatedDTO = userService.update(id, userUpdateDTO);
    return ResponseEntity.ok().body(updatedDTO);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id){
    userService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
