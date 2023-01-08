package com.growdev.ecommerce.dto.user.user;

import com.growdev.ecommerce.dto.user.UserInsertDTO;
import com.growdev.ecommerce.validation.user.UserPacienteDTOValidation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@UserPacienteDTOValidation
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPacienteDTO {
    public UserInsertDTO userInsertDTO;
    public PacienteDTO pacienteDTO;
}

