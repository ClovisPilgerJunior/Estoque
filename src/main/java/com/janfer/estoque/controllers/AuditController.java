package com.janfer.estoque.controllers;

import com.janfer.estoque.domain.audit.EntidadeComRevisao;
import com.janfer.estoque.domain.audit.GenericRevisionRepository;
import com.janfer.estoque.domain.entities.User;
import com.janfer.estoque.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/audit")
@CrossOrigin(origins = "http://localhost:4200")
public class AuditController {


  @Autowired
  private EntityManager entityManager;

  @Autowired
  private GenericRevisionRepository<User> genericRevisionRepository;

  @Autowired
  UserRepository userRepository;


  @RequestMapping("/{nome}")
  public ResponseEntity<List<EntidadeComRevisao<User>>> getRevisions(@PathVariable String nome) {
    Optional<User> user = userRepository.findByName(nome);
    List<EntidadeComRevisao<User>> revisoes = genericRevisionRepository.listaRevisoes(Long.valueOf(user.get().getId()),User.class);
    if(revisoes != null)
      return new ResponseEntity<List<EntidadeComRevisao<User>>>(revisoes, HttpStatus.OK);
    else
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @GetMapping("/v1")
  public List<Object[]> getAuditRecords(
      @RequestParam(name = "startDate")
      @DateTimeFormat(pattern = "yyyy-MM-dd 00:00") Date startDate,
      @RequestParam(name = "endDate")
      @DateTimeFormat(pattern = "yyyy-MM-dd 23:59") Date endDate) {

    AuditReader reader = AuditReaderFactory.get(entityManager);
    List<Object[]> results = reader.createQuery()
        .forRevisionsOfEntity(User.class, false, true)
        .add(AuditEntity.revisionProperty("revisaoData").between(startDate, endDate))
        .getResultList();

    return results;
  }


  @GetMapping("/data")
  public ResponseEntity<List<Object[]>> getUsersAudByRevisionDate(
      @RequestParam
      @DateTimeFormat(pattern = "yyyy-MM-dd 00:00") Date startDate,
      @RequestParam
      @DateTimeFormat(pattern = "yyyy-MM-dd 23:59") Date endDate) {
    List<Object[]> usersAud = userRepository.findUsersAudByRevisionDate(startDate, endDate);
    return ResponseEntity.ok().body(usersAud);
  }

}

