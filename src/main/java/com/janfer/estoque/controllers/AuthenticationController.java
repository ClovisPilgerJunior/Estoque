package com.janfer.estoque.controllers;

import com.janfer.estoque.domain.dtos.LoginResponseDTO;
import com.janfer.estoque.domain.dtos.UserGetDTO;
import com.janfer.estoque.security.TokenService;
import com.janfer.estoque.security.UserSS;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthenticationController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private TokenService tokenService;

  @PostMapping("/login")
  public ResponseEntity login(@RequestBody @Valid UserGetDTO userGetDTO) {

    var usernamePassword = new UsernamePasswordAuthenticationToken(userGetDTO.getName(), userGetDTO.getPassword());
    var auth = this.authenticationManager.authenticate(usernamePassword);

    var token = tokenService.generateToken((UserSS) auth.getPrincipal());
    return ResponseEntity.ok(new LoginResponseDTO(token));
  }
}