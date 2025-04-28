package com.storage.stockflow.controllers;

import com.storage.stockflow.domain.dtos.LoginResponseDTO;
import com.storage.stockflow.domain.dtos.UserGetDTO;
import com.storage.stockflow.repositories.UserRepository;
import com.storage.stockflow.security.TokenService;
import com.storage.stockflow.security.UserSS;
import com.storage.stockflow.services.exceptions.BadCredentialsException;
import com.storage.stockflow.services.exceptions.ProductDisableException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
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
  private UserRepository userRepository;

  @Autowired
  private TokenService tokenService;

  @PostMapping("/login")
  public ResponseEntity login(@RequestBody @Valid UserGetDTO userGetDTO, HttpSession session) {

    // --- Verificações PRÉ-AUTENTICAÇÃO (Opcionais, podem estar no UserDetailsService também) ---

    // Verificação se o usuário existe pelo nome (melhor ser genérico para evitar enumeração de usuários)
    // O AuthenticationManager lança BadCredentialsException se o usuário não for encontrado pelo UserDetailsService
    if(!userRepository.existsByName(userGetDTO.getName())){
      // Lançar BadCredentialsException genérica para não revelar se o usuário existe ou a senha está errada
      throw new BadCredentialsException("Usuário ou senha inválidos");
    }


    // Verificação se o usuário está ativo (Lógica de negócio antes de tentar autenticar)
    if(!userRepository.isUsersAtivoByName(userGetDTO.getName())){
      throw new ProductDisableException("Usuário está inativo no sistema"); // Use sua exceção customizada
    }

    // --- Autenticação SEGURA usando Spring Security ---
    try {
      // Cria o token de autenticação com nome de usuário e senha em texto plano
      var usernamePassword = new UsernamePasswordAuthenticationToken(userGetDTO.getName(), userGetDTO.getPassword());

      // Chama o AuthenticationManager. Ele encontrará o UserDetailsService, carregará o UserDetails (UserSS),
      // e usará o PasswordEncoder para comparar a senha fornecida com a senha HASHEADA do banco.
      // Se a senha estiver errada ou o usuário não for encontrado/válido, ele lançará AuthenticationException.
      var auth = this.authenticationManager.authenticate(usernamePassword);

      // Se authenticate() não lançou exceção, a autenticação foi bem-sucedida
      // auth.getPrincipal() contém o UserDetails (UserSS) retornado pelo UserDetailsService
      var token = tokenService.generateToken((UserSS) auth.getPrincipal());

      // Configuração de sessão (opcional, JWTs podem ser stateless)
      // session.setAttribute("accessToken", token);

      // Retorna o token no corpo da resposta
      return ResponseEntity.ok(new LoginResponseDTO(token));

    } catch (AuthenticationException e) {
      // Captura falhas de autenticação (senha incorreta, usuário não encontrado, etc.)
      // É útil logar a falha no lado do servidor por motivos de segurança e auditoria
      System.err.println("Falha de autenticação para o usuário " + userGetDTO.getName() + ": " + e.getMessage());

      // Lança uma exceção BadCredentialsException genérica.
      // O handler padrão do Spring Security para AuthenticationException tipicamente
      // transforma isso em uma resposta HTTP 401 Unauthorized.
      throw new BadCredentialsException("Usuário ou senha inválidos"); // Mensagem genérica é mais segura
      // Nota: Se o seu UserDetailsService também verificar o status 'ativo', a exceção
      // de usuário inativo pode ser lançada aqui em vez de antes.
    }
  }
}