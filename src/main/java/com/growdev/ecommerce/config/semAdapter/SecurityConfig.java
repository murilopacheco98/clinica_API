package com.growdev.ecommerce.config.semAdapter;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Autowired
    RsaKeyProperties rsaKeyProperties;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors()
                .and()
                .csrf().disable()
                .authorizeRequests(auth -> auth
                        .antMatchers("/oauth/post").permitAll()
                        .antMatchers("/authority/**").hasAuthority("SCOPE_ADMIN")

                        .antMatchers("/user/post/paciente").permitAll()
                        .antMatchers("/user/post/medico", "/user/get/pageable").hasAuthority("SCOPE_ADMIN")
                        .antMatchers("/user/get/{id}", "/user/put/{id}", "/user/delete/{id}")
                            .hasAnyAuthority("SCOPE_PACIENTE", "SCOPE_MEDICO")

                        .antMatchers("/medico/get/**").permitAll()
                        .antMatchers("/medico/put/{id}").hasAuthority("SCOPE_MEDICO")

                        .antMatchers("/paciente/get/pageable").hasAnyAuthority("SCOPE_MEDICO", "SCOPE_ADMIN")
                        .antMatchers("/paciente/get/{email}").hasAnyAuthority("SCOPE_PACIENTE", "SCOPE_MEDICO", "SCOPE_ADMIN")
                        .antMatchers("/paciente/put/{id}").hasAuthority("SCOPE_PACIENTE")

                        .antMatchers("/agendamento/get/**").permitAll()
                        .antMatchers("/agendamento/post", "/agendamento/put/**", "/agendamento/delete/**")
                            .hasAnyAuthority("SCOPE_PACIENTE","SCOPE_MEDICO")

                        .antMatchers("/consulta/get/**").permitAll()
                        .antMatchers("/consulta/**").hasAuthority("SCOPE_MEDICO")

                        .antMatchers("/especialidade/get/**").permitAll()
                        .antMatchers("/especialidade/**").hasAuthority("SCOPE_ADMIN")

                        .antMatchers("/horario/get/**").permitAll()
                        .antMatchers("/horario/**").hasAuthority("SCOPE_ADMIN")

                        .anyRequest().authenticated())
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling((ex) -> ex
                         .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                         .accessDeniedHandler(new BearerTokenAccessDeniedHandler()))
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeyProperties.publicKey()).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(rsaKeyProperties.publicKey()).privateKey(rsaKeyProperties.privateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        final CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of("http://localhost:3000/"));
//        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT", "OPTIONS", "PATCH", "DELETE"));
//        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type", "Access-Control-Allow-Origin"));
//        configuration.setAllowCredentials(true);
//        configuration.setExposedHeaders(List.of("Authorization", "Access-Control-Allow-Origin"));
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
}
