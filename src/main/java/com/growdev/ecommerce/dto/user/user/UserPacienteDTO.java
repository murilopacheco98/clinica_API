package com.growdev.ecommerce.dto.user.user;

import com.growdev.ecommerce.dto.user.UserInsertDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPacienteDTO {
    private UserInsertDTO userInsertDTO;
    private PacienteDTO pacienteDTO;
}

