package com.growdev.ecommerce.dto.user;

import com.growdev.ecommerce.entities.Authority;
import com.growdev.ecommerce.entities.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
  private Long id;
  @NotBlank(message = "O campo é obrigatório")
  private String email;
  private String codigoVerificador;
  private Set<Authority> authority = new HashSet<>();

  public UserDTO(UserEntity userEntity) {
    this.id = userEntity.getId();
    this.email = userEntity.getEmail();
    this.codigoVerificador = userEntity.getCodigoVerificador();
    this.authority.addAll(userEntity.getAuthority());
  }
}
