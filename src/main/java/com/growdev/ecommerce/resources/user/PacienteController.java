package com.growdev.ecommerce.resources.user;

import com.growdev.ecommerce.dto.user.user.PacienteDTO;
import com.growdev.ecommerce.services.user.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/paciente")
public class PacienteController {
    @Autowired
    PacienteService pacienteService;

    @GetMapping("/get/pageable")
    public ResponseEntity<Page<PacienteDTO>> getAllPageable(
            @RequestParam(value = "pagina", defaultValue = "0") Integer pagina,//Primeira página
            @RequestParam(value = "linhasPorPagina", defaultValue = "1") Integer linhasPorPagina,//quantidade de registros por pagina
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,//direção Crescente
            @RequestParam(value = "ordenado", defaultValue = "nome") String nome //Ordem
            //exemplo de URL: /medico/get/pageable?pagina=0&linhasPorPagina=12&nome=teste
    ) {
        PageRequest list = PageRequest.of(pagina, linhasPorPagina, Sort.Direction.valueOf(direction), nome);
        Page<PacienteDTO> authorities = pacienteService.findAllPageable(list);
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