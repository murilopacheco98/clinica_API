package com.growdev.ecommerce.resources;

import com.growdev.ecommerce.dto.AgendamentoDTO;
import com.growdev.ecommerce.services.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/agendamento")
public class AgendamentoController {
  @Autowired
  AgendamentoService agendamentoService;

  @GetMapping("/get/pageable")
  public ResponseEntity<Page<AgendamentoDTO>> findAllPageable(
    @RequestParam(value = "pagina", defaultValue = "0") Integer pagina,//Primeira página
    @RequestParam(value = "linhasPorPagina", defaultValue = "1") Integer linhasPorPagina,//quantidade de registros por pagina
    @RequestParam(value = "direction", defaultValue = "ASC") String direction,//direção Crescente
    @RequestParam(value = "ordenado", defaultValue = "dataConsulta") String nome //Ordem
    //exemplo de URL: /city/get/pageable?pagina=0&linhasPorPagina=12&nome=teste
  ) {
    PageRequest list = PageRequest.of(pagina, linhasPorPagina, Sort.Direction.valueOf(direction), nome);
    Page<AgendamentoDTO> cityDTOS = agendamentoService.findAllPaged(list);
    return ResponseEntity.ok().body(cityDTOS);
  }

  @GetMapping("/get/all")
  public ResponseEntity<List<AgendamentoDTO>> findAllable() {
    return ResponseEntity.ok().body(agendamentoService.findAll());
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<AgendamentoDTO> findById(@PathVariable("id") Long id) {
    AgendamentoDTO agendamentoDTO = agendamentoService.findById(id);
    return ResponseEntity.ok().body(agendamentoDTO);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
    agendamentoService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/post")
  public ResponseEntity<AgendamentoDTO> insert(@RequestBody AgendamentoDTO agendamentoDTO) {
    agendamentoDTO = agendamentoService.create(agendamentoDTO);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
      .buildAndExpand(agendamentoDTO.getId()).toUri();
    return ResponseEntity.created(uri).body(agendamentoDTO);
  }

  @PutMapping("/put/{id}")
  public ResponseEntity<AgendamentoDTO> atualizar(@Valid @RequestBody AgendamentoDTO agendamentoDTO, @PathVariable Long id) {
    agendamentoDTO = agendamentoService.update(agendamentoDTO, id);
    return ResponseEntity.ok().body(agendamentoDTO);
  }
}
