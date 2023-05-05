package com.growdev.ecommerce.config.semAdapter;

import com.growdev.ecommerce.dto.user.UserDTO;
import lombok.*;

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