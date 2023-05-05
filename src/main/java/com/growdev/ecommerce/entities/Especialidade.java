package com.growdev.ecommerce.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "especialidade")
public class Especialidade implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String nome;
    @Column(name = "descricao", columnDefinition = "TEXT", nullable = false)
    private String descricao;

//    @ManyToMany(mappedBy = "especialidade")
//    private Set<Medico> medicos = new HashSet<>();
}
