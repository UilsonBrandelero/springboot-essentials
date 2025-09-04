package springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
    prePostEnabled = true
)
public class SecurityConfig {
    @Bean
    // Cria um filtro para as requisicoes
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // Habilita o suporte a tokens CSRF
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/public/**","/public").permitAll() // Define quais rotas não precisam de autenticação
                        .requestMatchers("/animes/admin/**").hasRole("ADMIN") //Define que para estas rotas a regra de acesso deve ser admin
                        .requestMatchers("/animes/**").hasRole("USER") //Define que para estas rotas a regra de acesso deve ser user
                        //A ordem deve ser da mais restritiva para a menos restritiva
                        .anyRequest().authenticated()) // Qualquer outra requisição deve ser autenticada
                .formLogin(Customizer.withDefaults()) // login com formulario padrao
                .httpBasic(Customizer.withDefaults()); // login com envio dos dados pela requisição
        return http.build();
    }
    @Bean 
    public WebSecurityCustomizer webSecurityCustomizer(){
        return(web) -> web.ignoring().requestMatchers("/public/**");
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    // @Bean
    // public UserDetailsService userDetailsService(PasswordEncoder encoder) {
    //     // Cria um usuaruio da aplicação. Usuario em memoria
    //     UserDetails user = User.withUsername("admin")
    //             .password(encoder.encode("1234"))
    //             .roles("ADMIN")
    //             .build();
    //     UserDetails user2 = User.withUsername("user")
    //             .password(encoder.encode("1234"))
    //             .roles("USER")
    //             .build();
        
        

    //     return new InMemoryUserDetailsManager(user,user2);
    // }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
