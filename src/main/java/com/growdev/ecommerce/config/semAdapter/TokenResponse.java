package com.growdev.ecommerce.config.semAdapter;

import com.growdev.ecommerce.dto.user.UserDTO;
import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TokenResponse {
    private Long id;
    private UserDTO userDTO;
    private String token;
    private String timeToken;
    private String expirationDate;

    public TokenResponse(UserDTO userDTO, String token, String timeToken, String messageExpirationDate) {
        this.userDTO = userDTO;
        this.token = token;
        this.timeToken = timeToken;
        this.expirationDate = messageExpirationDate;
    }
}