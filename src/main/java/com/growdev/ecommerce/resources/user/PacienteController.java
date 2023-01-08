package com.growdev.ecommerce.resources.user;

import com.growdev.ecommerce.dto.user.user.PacienteDTO;
import com.growdev.ecommerce.services.user.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/paciente")
public class PacienteController {
    @Autowired
    PacienteService pacienteService;

    // page, size, direction, order
    @GetMapping("/get/pageable")
    public ResponseEntity<Page<PacienteDTO>> getAllPageable(Pageable pageable) {
        Page<PacienteDTO> authorities = pacienteService.findAllPageable(pageable);
        return ResponseEntity.ok().body(authorities);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PacienteDTO> getById(@PathVariable("id") Long id) {
        PacienteDTO pacienteDTO = pacienteService.findById(id);
        return ResponseEntity.ok().body(pacienteDTO);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<PacienteDTO> put(@Valid @RequestBody PacienteDTO pacienteDTO, @PathVariable Long id) {
        pacienteDTO = pacienteService.update(pacienteDTO, id);
        return ResponseEntity.ok().body(pacienteDTO);
    }
}