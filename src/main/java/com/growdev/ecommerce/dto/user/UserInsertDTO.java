package com.growdev.ecommerce.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInsertDTO extends UserDTO {
  @NotBlank(message = "A senha é obrigatória.")
  private String senha;
}
