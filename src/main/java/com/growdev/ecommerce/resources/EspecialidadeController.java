package com.growdev.ecommerce.resources;

import com.growdev.ecommerce.entities.Especialidade;
import com.growdev.ecommerce.services.EspecialidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/especialidade")
public class EspecialidadeController {
    @Autowired
    EspecialidadeService especialidadeService;

    @GetMapping("/get/pageable")
    public ResponseEntity<Page<Especialidade>> findAllPageable(
            @RequestParam(value = "pagina", defaultValue = "0") Integer pagina,//Primeira página
            @RequestParam(value = "linhasPorPagina", defaultValue = "1") Integer linhasPorPagina,//quantidade de registros por pagina
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,//direção Crescente
            @RequestParam(value = "ordenado", defaultValue = "nome") String nome //Ordem
    ) {
        PageRequest list = PageRequest.of(pagina, linhasPorPagina, Sort.Direction.valueOf(direction), nome);
        Page<Especialidade> authorities = especialidadeService.findAllPaged(list);
        return ResponseEntity.ok().body(authorities);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<Especialidade>> getAllPageable() {
        return ResponseEntity.ok().body(especialidadeService.findAll());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Especialidade> getById(@PathVariable("id") Long id) {
        Especialidade especialidade = especialidadeService.findById(id);
        return ResponseEntity.ok().body(especialidade);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        especialidadeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/post")
    public ResponseEntity<Especialidade> post(@Valid @RequestBody Especialidade especialidade) {
        especialidade = especialidadeService.create(especialidade);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(especialidade.getId()).toUri();
        return ResponseEntity.created(uri).body(especialidade);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<Especialidade> put(@Valid @RequestBody Especialidade especialidade, @PathVariable Long id) {
        especialidade = especialidadeService.update(especialidade, id);
        return ResponseEntity.ok().body(especialidade);
    }
}

