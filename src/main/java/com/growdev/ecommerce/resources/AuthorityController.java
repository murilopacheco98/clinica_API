package com.growdev.ecommerce.resources;

import com.growdev.ecommerce.entities.Authority;
import com.growdev.ecommerce.services.AuthorityService;
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
@RequestMapping(value = "/authority")
public class AuthorityController {
    @Autowired
    AuthorityService authorityService;

    @GetMapping("/get/pageable")
    public ResponseEntity<Page<Authority>> findAllPageable(
            @RequestParam(value = "pagina", defaultValue = "0") Integer pagina,//Primeira página
            @RequestParam(value = "linhasPorPagina", defaultValue = "1") Integer linhasPorPagina,//quantidade de registros por pagina
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,//direção Crescente
            @RequestParam(value = "ordenado", defaultValue = "authority") String nome //Ordem
            //exemplo de URL: /city/pageable?pagina=0&linhasPorPagina=12&nome=teste
    ) {
        PageRequest list = PageRequest.of(pagina, linhasPorPagina, Sort.Direction.valueOf(direction), nome);
        Page<Authority> authorities = authorityService.findAllPaged(list);
        return ResponseEntity.ok().body(authorities);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<Authority>> getAllPageable() {
        return ResponseEntity.ok().body(authorityService.findAll());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Authority> getById(@PathVariable("id") Long id) {
        Authority authority = authorityService.findById(id);
        return ResponseEntity.ok().body(authority);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        authorityService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/post")
    public ResponseEntity<Authority> post(@Valid @RequestBody Authority authority) {
        authority = authorityService.create(authority);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(authority.getId()).toUri();
        return ResponseEntity.created(uri).body(authority);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<Authority> put(@Valid @RequestBody Authority authority, @PathVariable Long id) {
        authority = authorityService.create(authority);
        return ResponseEntity.ok().body(authority);
    }
}

