package com.growdev.ecommerce.resources;

import com.growdev.ecommerce.entities.Horario;
import com.growdev.ecommerce.services.HorarioService;
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
@RequestMapping(value = "/horario")
public class HorarioController {
    @Autowired
    HorarioService horarioService;

    @GetMapping("/get/pageable")
    public ResponseEntity<Page<Horario>> getAllPageable(
            @RequestParam(value = "pagina", defaultValue = "0") Integer pagina,//Primeira página
            @RequestParam(value = "linhasPorPagina", defaultValue = "1") Integer linhasPorPagina,//quantidade de registros por pagina
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,//direção Crescente
            @RequestParam(value = "ordenado", defaultValue = "horaMinuto") String nome //Ordem
            //exemplo de URL: /city/pageable?pagina=0&linhasPorPagina=12&nome=teste
    ) {
        PageRequest list = PageRequest.of(pagina, linhasPorPagina, Sort.Direction.valueOf(direction), nome);
        Page<Horario> authorities = horarioService.findAllPaged(list);
        return ResponseEntity.ok().body(authorities);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<Horario>> getAll() {
        return ResponseEntity.ok().body(horarioService.findAll());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Horario> getById(@PathVariable("id") Long id) {
        Horario horario = horarioService.findById(id);
        return ResponseEntity.ok().body(horario);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        horarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/post")
    public ResponseEntity<Horario> post(@RequestBody Horario horario) {
        horario = horarioService.create(horario);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(horario.getId()).toUri();
        return ResponseEntity.created(uri).body(horario);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<Horario> put(@RequestBody Horario horario, @PathVariable Long id) {
        horario = horarioService.create(horario);
        return ResponseEntity.ok().body(horario);
    }
}
