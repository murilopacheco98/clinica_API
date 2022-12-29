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
  public ResponseEntity<AgendamentoDTO> insert(@RequestBody AgendamentoDTO cityDTO) {
    cityDTO = agendamentoService.create(cityDTO);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
      .buildAndExpand(cityDTO.getId()).toUri();
    return ResponseEntity.created(uri).body(cityDTO);
  }

  @PutMapping("/put/{id}")
  public ResponseEntity<AgendamentoDTO> atualizar(@Valid @RequestBody AgendamentoDTO cityDTO, @PathVariable Long id) {
    cityDTO = agendamentoService.update(cityDTO, id);
    return ResponseEntity.ok().body(cityDTO);
  }
}
