package com.janfer.estoque.configs;

import com.janfer.estoque.security.SecurityFilter;
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
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.Arrays;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
import static org.springframework.security.config.Customizer.withDefaults;

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
        .cors(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(mvc.pattern("/auth/login")).permitAll()
            .requestMatchers(mvc.pattern("/h2-console/*")).permitAll()
            .anyRequest().permitAll()
        )
        .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }

//  private static final String API_URL_PATTERN = "/*";
//
//  @Bean
//  public SecurityFilterChain getSecurityFilterChain(HttpSecurity http,
//                                                    HandlerMappingIntrospector introspector) throws Exception {
//    MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
//
//    http.csrf(csrfConfigurer ->
//        csrfConfigurer.ignoringRequestMatchers(mvcMatcherBuilder.pattern(API_URL_PATTERN),
//            PathRequest.toH2Console()));
//
//    if (Arrays.asList(env.getActiveProfiles()).contains("dev")) {
//      http
//          .headers((headers) ->
//              headers
//                  .defaultsDisabled()
//                  .cacheControl(withDefaults())
//                  .frameOptions(withDefaults())
//          );
//    }
//
//    http.authorizeHttpRequests(auth ->
//        auth
//            .requestMatchers(mvcMatcherBuilder.pattern(API_URL_PATTERN)).permitAll()
//            //This line is optional in .authenticated() case as .anyRequest().authenticated()
//            //would be applied for H2 path anyway
//            .requestMatchers(PathRequest.toH2Console()).authenticated()
//            .anyRequest().authenticated()
//    );
//
//    http.formLogin(Customizer.withDefaults());
//    http.httpBasic(Customizer.withDefaults());
//
//    return http.build();
//  }

  @Value("${cors.origins}")
  private String corsOrigins;

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*").allowedOrigins(corsOrigins);
      }
    };
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
    return authConfiguration.getAuthenticationManager();
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
