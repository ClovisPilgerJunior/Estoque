package com.janfer.estoque.configs;

import com.janfer.estoque.security.SecurityFilter;
import com.janfer.estoque.services.exceptions.AccessDeniedException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.Arrays;
import java.util.List;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasRole;
import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Profile("dev")
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

    if (Arrays.asList(env.getActiveProfiles()).contains("dev")) {
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
  public PasswordEncoder bCryptPasswordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }

}
