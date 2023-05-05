package com.growdev.ecommerce.config.semAdapter;

import com.growdev.ecommerce.dto.user.UserDTO;
import com.growdev.ecommerce.entities.user.UserEntity;
import com.growdev.ecommerce.exceptions.exception.ResourceNotFoundException;
import com.growdev.ecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class TokenService {
    @Autowired
    JwtEncoder jwtEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    public TokenResponse generateToken(String email, String senha) {
        Instant instantNow = Instant.now();
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("Email inválido.");
        }
        if (!passwordEncoder.matches(senha, user.getPassword())) {
            throw new ResourceNotFoundException("Senha inválida.");
        }
        String scope = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(instantNow.atZone(ZoneId.systemDefault()).toInstant())
                .expiresAt(instantNow.atZone(ZoneId.systemDefault()).toInstant().plus(24, ChronoUnit.HOURS))
                .subject(user.getEmail())
                .claim("scope", scope)
                .build();
        String token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        Instant expirationDate = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getExpiresAt();
//        Instant issuedDate = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getIssuedAt();
        String timeToken = "24:00:00 hours";

        String[] splitExpirationDate = String.valueOf(expirationDate).split("T");
        String messageExpirationDate = splitExpirationDate[0] + " " + Arrays.toString(splitExpirationDate[1].split("\\."));

        UserDTO userDTO = new UserDTO(user);
        return new TokenResponse(user.getId(), userDTO, token, timeToken, messageExpirationDate);
    }
}
