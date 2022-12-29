package com.growdev.ecommerce.resources.user;

import com.growdev.ecommerce.dto.user.medico.MedicoDTO;
import com.growdev.ecommerce.services.user.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<Page<MedicoDTO>> findAllPageable(
            @RequestParam(value = "pagina", defaultValue = "0") Integer pagina,//Primeira página
            @RequestParam(value = "linhasPorPagina", defaultValue = "1") Integer linhasPorPagina,//quantidade de registros por pagina
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,//direção Crescente
            @RequestParam(value = "ordenado", defaultValue = "nome") String nome //Ordem
            //exemplo de URL: /medico/get/pageable?pagina=0&linhasPorPagina=12&nome=teste
    ) {
        PageRequest list = PageRequest.of(pagina, linhasPorPagina, Sort.Direction.valueOf(direction), nome);
        Page<MedicoDTO> authorities = medicoService.findAllPaged(list);
        return ResponseEntity.ok().body(authorities);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<MedicoDTO> getById(@PathVariable("id") Long id) {
        MedicoDTO medicoDTO = medicoService.findById(id);
        return ResponseEntity.ok().body(medicoDTO);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<MedicoDTO> put(@Valid @RequestBody MedicoDTO medicoDTO, @PathVariable Long id) {
        medicoDTO = medicoService.update(medicoDTO, id);
        return ResponseEntity.ok().body(medicoDTO);
    }
}
