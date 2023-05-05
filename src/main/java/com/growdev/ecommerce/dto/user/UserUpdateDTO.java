package com.growdev.ecommerce.dto.user;

import com.growdev.ecommerce.validation.user.UserUpdateValidation;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@UserUpdateValidation
@Getter
@Setter
public class UserUpdateDTO extends UserDTO {
    @NotBlank(message = "A senha é obrigatória.")
    private String senha;
}
