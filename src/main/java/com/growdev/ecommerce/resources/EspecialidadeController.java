package com.growdev.ecommerce.resources;

import com.growdev.ecommerce.entities.Especialidade;
import com.growdev.ecommerce.services.EspecialidadeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/especialidade")
public class EspecialidadeController {
    @Autowired
    EspecialidadeService especialidadeService;

    @GetMapping("/get/all")
    public ResponseEntity<List<Especialidade>> findAll() {
        List<Especialidade> especialidadeList = especialidadeService.findAllOrderByNomeAsc();
        return ResponseEntity.ok().body(especialidadeList);
    }

    @GetMapping("/get/pageable")
    public ResponseEntity<Page<Especialidade>> findAllPageable(@RequestParam(defaultValue = "0") Integer page,
                                                               @RequestParam(defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "nome");
        Page<Especialidade> authorities = especialidadeService.findAllPaged(pageable);
        return ResponseEntity.ok().body(authorities);
    }

    @GetMapping("/get/search")
    public ResponseEntity<Page<Especialidade>> search(@RequestParam(defaultValue = "0") Integer page,
                                                      @RequestParam(defaultValue = "10") Integer size, @RequestParam String search) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "nome");
        Page<Especialidade> especialidadePage = especialidadeService.searchEspecialidade(search, pageable);
        return ResponseEntity.ok().body(especialidadePage);
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

