package com.growdev.ecommerce.entities;

import com.growdev.ecommerce.entities.user.Medico;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
