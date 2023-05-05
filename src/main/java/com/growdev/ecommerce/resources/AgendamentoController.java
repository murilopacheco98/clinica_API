package com.growdev.ecommerce.resources;

import com.growdev.ecommerce.dto.AgendamentoDTO;
import com.growdev.ecommerce.dto.auxiliar.AgendamentoAux;
import com.growdev.ecommerce.dto.auxiliar.DisponibilidadeDate;
import com.growdev.ecommerce.services.AgendamentoService;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/agendamento")
public class AgendamentoController {
  @Autowired
  AgendamentoService agendamentoService;

  @GetMapping("/get/pageable")
  public ResponseEntity<Page<AgendamentoDTO>> findAllPageable(Pageable pageable) {
    Page<AgendamentoDTO> cityDTOS = agendamentoService.findAllPaged(pageable);
    return ResponseEntity.ok().body(cityDTOS);
  }

  @GetMapping("/get/medico/{id}")
  public ResponseEntity<Page<AgendamentoDTO>> findAllByMedicoId(@PathVariable("id") Long id, Pageable pageable) {
    Page<AgendamentoDTO> agendamentoDTOs = agendamentoService.findByMedicoId(id, pageable);
    return ResponseEntity.ok().body(agendamentoDTOs);
  }

  @GetMapping("/get/paciente/{id}")
  public ResponseEntity<Page<AgendamentoDTO>> findAllByPacienteId(@PathVariable("id") Long id, Pageable pageable) {
    Page<AgendamentoDTO> agendamentoDTOs = agendamentoService.findByPacienteId(id, pageable);
    return ResponseEntity.ok().body(agendamentoDTOs);
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<AgendamentoDTO> findById(@PathVariable("id") Long id) {
    AgendamentoDTO agendamentoDTO = agendamentoService.findById(id);
    return ResponseEntity.ok().body(agendamentoDTO);
  }

  @PostMapping("/check/availability")
  public ResponseEntity<List<DisponibilidadeDate>> getAvailability(@RequestBody EspecialidadeAux especialidadeAux) {
    List<DisponibilidadeDate> disponibilidadeDateList = agendamentoService.checkAvailability(especialidadeAux.getEspecialidade());
    return ResponseEntity.ok().body(disponibilidadeDateList);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
    agendamentoService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/post")
  public ResponseEntity<AgendamentoDTO> insert(@RequestBody AgendamentoAux agendamentoAux) {
    AgendamentoDTO agendamentoDTO = agendamentoService.create(agendamentoAux);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
      .buildAndExpand(agendamentoDTO.getId()).toUri();
    return ResponseEntity.created(uri).body(agendamentoDTO);
  }

  @PutMapping("/put/{id}")
  public ResponseEntity<AgendamentoDTO> atualizar(@Valid @RequestBody AgendamentoDTO agendamentoDTO, @PathVariable Long id) {
    agendamentoDTO = agendamentoService.update(agendamentoDTO, id);
    return ResponseEntity.ok().body(agendamentoDTO);
  }

  @Data
  public static class EspecialidadeAux {
    private String especialidade;
  }
}
