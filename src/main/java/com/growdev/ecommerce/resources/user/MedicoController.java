package com.growdev.ecommerce.resources.user;

import com.growdev.ecommerce.dto.user.medico.MedicoDTO;
import com.growdev.ecommerce.dto.user.user.PacienteDTO;
import com.growdev.ecommerce.services.user.MedicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/medico")
public class MedicoController {
    @Autowired
    MedicoService medicoService;

    @GetMapping("/get/pageable")
    public ResponseEntity<Page<MedicoDTO>> findAllPageable(Pageable pageable) {
        Page<MedicoDTO> medicoDTOs = medicoService.findAllPaged(pageable);
        return ResponseEntity.ok().body(medicoDTOs);
    }

    @GetMapping("/get/{email}")
    public ResponseEntity<MedicoDTO> getById(@PathVariable("email") String email) {
        MedicoDTO medicoDTO = medicoService.findByEmail(email);
        return ResponseEntity.ok().body(medicoDTO);
    }

    @GetMapping("/get/pacientes/{id}")
    public ResponseEntity<Page<PacienteDTO>> getMedicoPaciente(@PathVariable("id") Long id, Pageable pageable) {
        Page<PacienteDTO> pacienteDTOs = medicoService.findPacientes(id, pageable);
        return ResponseEntity.ok().body(pacienteDTOs);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<MedicoDTO> put(@Valid @RequestBody MedicoDTO medicoDTO, @PathVariable Long id) {
        medicoDTO = medicoService.update(medicoDTO, id);
        return ResponseEntity.ok().body(medicoDTO);
    }
}
