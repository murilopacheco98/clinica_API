package com.growdev.ecommerce.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.growdev.ecommerce.dto.user.UserDTO;
import com.growdev.ecommerce.dto.user.medico.MedicoDTO;
import com.growdev.ecommerce.entities.Agendamento;
import com.growdev.ecommerce.entities.Consulta;
import com.growdev.ecommerce.entities.Especialidade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "medico")
public class Medico extends AbstractEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "nome_profissional", unique = true, nullable = false)
    private String nomeJaleco;
    @Column(name = "crm", unique = true, nullable = false)
    private Integer crm;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "data_inscricao", nullable = false)
    private LocalDate dataInscricao;
    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "medico_especialidade",
            joinColumns = @JoinColumn(name = "id_medico", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_especialidade", referencedColumnName = "id")
    )
    private Set<Especialidade> especialidade = new HashSet<>();
    @JsonIgnore
    @OneToMany(mappedBy = "medico")
    private Set<Agendamento> agendamentos = new HashSet<>();

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id_usuario")
    private UserEntity userEntity;
    @JsonIgnore
    @OneToMany(mappedBy = "paciente")
    private Set<Consulta> consultas = new HashSet<>();

    public Medico(MedicoDTO medicoDTO) {
        this.nome = medicoDTO.getNome();
        this.crm = medicoDTO.getCrm();
        this.dataInscricao = medicoDTO.getDataInscricao();
        medicoDTO.getAgendamentoDTOs().forEach(agendamentoDTO -> this.agendamentos.add(new Agendamento(agendamentoDTO)));
        this.especialidade.addAll(medicoDTO.getEspecialidade());
        medicoDTO.getConsultaDTOs().forEach(consultaDTO -> this.consultas.add(new Consulta(consultaDTO)));
//        this.userEntity = new UserEntity(medicoDTO.getUserDTO());
    }
}
