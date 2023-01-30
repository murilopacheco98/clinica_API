package com.growdev.ecommerce.resources.user;

import com.growdev.ecommerce.dto.user.medico.MedicoDTO;
import com.growdev.ecommerce.services.user.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @PutMapping("/put/{id}")
    public ResponseEntity<MedicoDTO> put(@Valid @RequestBody MedicoDTO medicoDTO, @PathVariable Long id) {
        medicoDTO = medicoService.update(medicoDTO, id);
        return ResponseEntity.ok().body(medicoDTO);
    }
}
