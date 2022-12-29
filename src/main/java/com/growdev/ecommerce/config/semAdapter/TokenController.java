package com.growdev.ecommerce.config.semAdapter;

import com.growdev.ecommerce.config.semAdapter.TokenReponse;
import com.growdev.ecommerce.config.semAdapter.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/oauth")
public class TokenController {
    @Autowired
    TokenService tokenService;

    @PostMapping("/post")
    public ResponseEntity<TokenReponse> signIn(@RequestParam String email, @RequestParam String senha) {
        TokenReponse token = tokenService.generateToken(email, senha);
        return ResponseEntity.ok().body(token);
    }
}
