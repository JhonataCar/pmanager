package pmanager.infrastucture.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pmanager.infrastucture.security.AuthenticationFilter;
import pmanager.infrastucture.security.AuthenticationService;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/*
Esse componente garante que todas as requisições para os endpoints
/projects (e outros) sejam autenticadas. Ele:

- Define a política de autenticação sem estado (stateless).
- Desativa o CSRF, pois o sistema utiliza autenticação via token em vez de sessões.
- Implementa um filtro de autenticação personalizado (`AuthenticationFilter`)
para validar as credenciais antes que a requisição chegue aos endpoints REST.
*/

@Configuration
@EnableWebSecurity
@SuppressWarnings("unused")
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationService authenticationService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(c ->
                        c.sessionCreationPolicy(STATELESS)
                )
                .authorizeHttpRequests(r ->
                    r.requestMatchers("/**").authenticated()
                )
                .addFilterBefore(
                        new AuthenticationFilter(authenticationService),
                        UsernamePasswordAuthenticationFilter.class
                )
                .build();
    }
}
