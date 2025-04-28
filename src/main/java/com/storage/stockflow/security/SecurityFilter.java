package com.storage.stockflow.security;

import com.storage.stockflow.domain.entities.User;
import com.storage.stockflow.repositories.UserRepository;
import com.storage.stockflow.services.UserDetailsServiceImpl;
import com.storage.stockflow.services.exceptions.TokenExpiredException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {

  private final UserDetailsServiceImpl customUserDetailsService;

  public SecurityFilter(UserDetailsServiceImpl customUserDetailsService) {
    this.customUserDetailsService = customUserDetailsService;
  }

  @Autowired
  TokenService tokenService;

  @Autowired
  UserRepository userRepository;


  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain
  ) throws ServletException, IOException {
    var token = this.recoverToken(request);
    if(token !=null){
      try {
        var name = tokenService.validateToken(token);
        Optional<User> userOptional = userRepository.findByName(name);
        if (userOptional.isPresent()) {

          UserDetails userDetails = customUserDetailsService.loadUserByUsername(name);
          var authentication = new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());

          // Set the authentication in the SecurityContext (if needed)
          SecurityContextHolder.getContext().setAuthentication(authentication);
          System.out.println("SecurityFilet: Caiu no IF");
        }
      } catch (TokenExpiredException e) {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token de acesso expirado");
        return;
      }

    }
    filterChain.doFilter(request, response);
  }

  private String recoverToken(HttpServletRequest request) {
    var authHeader = request.getHeader("Authorization");
    if(authHeader == null) return null;
    return authHeader.replace("Bearer ", "");
  }
}
