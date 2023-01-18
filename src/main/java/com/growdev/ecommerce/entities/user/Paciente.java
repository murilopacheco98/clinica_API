package com.growdev.ecommerce.entities.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.growdev.ecommerce.dto.user.user.PacienteDTO;
import com.growdev.ecommerce.entities.Agendamento;
import com.growdev.ecommerce.entities.Consulta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "paciente")
public class Paciente extends AbstractEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "data_nascimento", nullable = false)
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dataNascimento;

    private String telefone;

    @Column(unique = true, nullable = false)
    private String cpf;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id_usuario")
    private UserEntity usuario;

    @JsonIgnore
    @OneToMany(mappedBy = "paciente")
    private Set<Agendamento> agendamentos = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "paciente")
    private Set<Consulta> consultas = new HashSet<>();

    public Paciente(PacienteDTO pacienteDTO) {
        this.nome = pacienteDTO.getNome();
        this.cpf = pacienteDTO.getCpf();
        this.telefone = pacienteDTO.getTelefone();
        this.dataNascimento = pacienteDTO.getDataNascimento();
        this.usuario = new UserEntity(pacienteDTO.getUserDTO());
    }
}
