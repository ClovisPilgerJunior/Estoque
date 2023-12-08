package com.janfer.estoque.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

  @Value("${jwt.secret}")
  private String secretPhrase;

  public String generateToken(UserSS userSS) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secretPhrase);
      return JWT.create()
          .withIssuer("estoque-api")
          .withSubject(userSS.getUsername())
          .withExpiresAt(genExpirationDate())
          .sign(algorithm);
    } catch (JWTCreationException e){
      throw new RuntimeException("Error while generating token ", e);
    }
  }

  private Instant genExpirationDate(){
    return LocalDateTime.now().plusMinutes(60).toInstant(ZoneOffset.of("-03:00"));
  }

  public String validateToken(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secretPhrase);
      return JWT.require(algorithm)
          .withIssuer("estoque-api")
          .build()
          .verify(token)
          .getSubject();
    } catch (JWTVerificationException e){
      System.out.println("Token Service: caiu aqui");
      return "";
    }
  }
}