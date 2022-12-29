package com.growdev.ecommerce.resources;

import com.growdev.ecommerce.dto.ConsultaDTO;
import com.growdev.ecommerce.services.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/consulta")
public class ConsultaController {

  @Autowired
  ConsultaService consultaService;

  @GetMapping("/get/pageable")
  public ResponseEntity<Page<ConsultaDTO>> findAll(
    @RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
    @RequestParam(value = "linhasPorPagina", defaultValue = "10") Integer linhasPorPagina,
    @RequestParam(value = "ordenado", defaultValue = "dataConsulta") String nome,
    @RequestParam(value = "direction", defaultValue = "ASC") String direction//direção Crescente
  ) {
    //qual pagina que eu quero (0), quantidade linhas, direação, ordenação
    PageRequest list = PageRequest.of(pagina, linhasPorPagina, Sort.Direction.valueOf(direction), nome);
    Page<ConsultaDTO> dtos = consultaService.findAll(list);
    return ResponseEntity.ok().body(dtos);
  }

  @PostMapping("/post")
  public ResponseEntity<ConsultaDTO> create(@RequestBody ConsultaDTO consultaDTO) {
    consultaService.create(consultaDTO);
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
