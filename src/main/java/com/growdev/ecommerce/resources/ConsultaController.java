package com.growdev.ecommerce.resources;

import com.growdev.ecommerce.dto.ConsultaDTO;
import com.growdev.ecommerce.dto.auxiliar.CreateConsultaAux;
import com.growdev.ecommerce.services.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/consulta")
public class ConsultaController {
  @Autowired
  ConsultaService consultaService;

  @GetMapping("/get/pageable")
  public ResponseEntity<Page<ConsultaDTO>> findAll(Pageable pageable) {
    Page<ConsultaDTO> dtos = consultaService.findAll(pageable);
    return ResponseEntity.ok().body(dtos);
  }

  @GetMapping("/get/pageable/paciente/{id}")
  public ResponseEntity<Page<ConsultaDTO>> findAllByPacienteId(@PathVariable("id") Long id, Pageable pageable) {
    Page<ConsultaDTO> consultaDTOs = consultaService.findAllByPacienteId(id, pageable);
    return ResponseEntity.ok().body(consultaDTOs);
  }

  @GetMapping("/get/pageable/medico/{id}")
  public ResponseEntity<Page<ConsultaDTO>> findAllByMedicoId(@PathVariable("id") Long id, Pageable pageable) {
    Page<ConsultaDTO> consultaDTOs = consultaService.findAllByMedicoId(id, pageable);
    return ResponseEntity.ok().body(consultaDTOs);
  }

  @PostMapping("/post")
  public ResponseEntity<ConsultaDTO> create(@RequestBody CreateConsultaAux createConsultaAux) {
    ConsultaDTO consultaDTO = consultaService.create(createConsultaAux);
    return ResponseEntity.ok().body(consultaDTO);
  }

  @PutMapping("/put/{id}")
  public ResponseEntity<ConsultaDTO> update(@PathVariable Long id, @RequestBody ConsultaDTO consultaDTO) {
    consultaService.update(consultaDTO, id);
    return ResponseEntity.ok().body(consultaDTO);
  }

//  @DeleteMapping("/delete/{id}")
//  public ResponseEntity<String> delete(@PathVariable Long id) {
//    consultaService.delete(id);
//    return ResponseEntity.ok().body("Event deleted successfully.");
//  }
}
