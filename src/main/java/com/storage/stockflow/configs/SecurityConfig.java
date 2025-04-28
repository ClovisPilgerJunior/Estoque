package com.storage.stockflow.configs;

import com.storage.stockflow.security.SecurityFilter;
import com.storage.stockflow.services.exceptions.AccessDeniedException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.Arrays;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Profile("test")
public class SecurityConfig {

  @Autowired
  SecurityFilter securityFilter;
  @Autowired
  private Environment env;


  @Bean
  MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
    return new MvcRequestMatcher.Builder(introspector);
  }


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc)
      throws Exception {

    if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
      http
          .headers((headers) ->
              headers
                  .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
          );
    }

    return http
        .cors(cors -> cors
            .configurationSource(request -> {
              CorsConfiguration configuration = new CorsConfiguration();
              configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
              configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
              configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
              configuration.setAllowCredentials(true);
              return configuration;
            })
        )
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .securityMatcher("/api/**", "/app/**")
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(mvc.pattern("/api/auth/login")).permitAll()
            .requestMatchers(mvc.pattern("/api/produtoEntrada/cadastrarAll")).hasRole("ADMIN")
            .requestMatchers(mvc.pattern("/h2-console/**")).permitAll()
            .anyRequest().authenticated()
        )
        .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(exceptionHandling -> exceptionHandling
            .authenticationEntryPoint((request, response, authException) -> {
              // Configurar uma resposta personalizada para usuário não autenticado
              response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Não autenticado");
            }).accessDeniedHandler((request, response, accessDeniedException) -> {
              throw new AccessDeniedException("Acesso negado");
            })
        )
        .build();
  }


  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
    return authConfiguration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
