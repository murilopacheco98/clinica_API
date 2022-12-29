package com.growdev.ecommerce.dto.user.medico;

import com.growdev.ecommerce.dto.user.UserInsertDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserMedicoDTO {
    private UserInsertDTO userInsertDTO;
    private MedicoDTO medicoDTO;
}

