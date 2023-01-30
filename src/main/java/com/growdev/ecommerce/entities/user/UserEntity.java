package com.growdev.ecommerce.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.growdev.ecommerce.dto.user.UserDTO;
import com.growdev.ecommerce.entities.Authority;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tb_user")
public class UserEntity implements UserDetails, Serializable {//vai ser a classe que retorna o acesso - spring security
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true, nullable = false)
  private String email;
  @Column(nullable = false)
  private String senha;
  @Column(name = "codigo_verificador", length = 6)
  private String codigoVerificador;
//  @JsonManagedReference
  @JsonIgnore
  @ManyToMany(fetch = FetchType.EAGER) // so pra forçar a buscar do usuario e nele vir as roles
  @JoinTable(
    name = "user_authority",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "authority_id")
  )
  private Set<Authority> authority = new HashSet<>();

  @Column(name = "data_criacao")
  private Instant criado;
  @Column(name = "data_atualizacao")
  private Instant atualizado;

  public UserEntity(UserDTO userDTO) {
    this.email = userDTO.getEmail();
//    this.senha = userDTO.get
    this.codigoVerificador = userDTO.getCodigoVerificador();
    this.authority.addAll(userDTO.getAuthority());
  }
  @PrePersist
  public void prePersist() {
    criado = Instant.now();
  }
  @PreUpdate
  public void preUpdate() {
    atualizado = Instant.now();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    //PERCORRENDO A LIST DE ROLES E MANDANDO ELA CONVERTIDA EM UMA LISTA DE GRANTEDAUTHORITY
    return authority.stream().map(authority-> new SimpleGrantedAuthority(authority.getAuthority())).collect(Collectors.toList());
  }
  @Override
  public String getPassword() {
    return senha;
  }
  @Override
  public String getUsername() {
    return email;
  }
  //COLOQUEI TODOS COMO TRUE, POIS O JWT + OAUTH já farão tudo isso, entao nao preciso de um algoritmo logico
  @Override
  public boolean isAccountNonExpired() {//TESTA SE A CONTA EXPIRADA
    return true;
  }
  @Override
  public boolean isAccountNonLocked() {//Testa se o usuário está bloqueado
    return true;
  }
  @Override
  public boolean isCredentialsNonExpired() {//Testa se as credenciais estão expiradas
    return true;
  }
  @Override
  public boolean isEnabled() {//habilita ou nao o usuario no sistema
    return true;
  }
}
