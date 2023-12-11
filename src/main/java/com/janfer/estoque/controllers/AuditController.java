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

  @RequestMapping(value = "/data", method = RequestMethod.GET)
  public List<Object[]> getAuditRecords(
      @RequestParam(name = "startDate")
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
      @RequestParam(name = "endDate")
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {

    AuditReader reader = AuditReaderFactory.get(entityManager);
    List<Object[]> results = reader.createQuery()
        .forRevisionsOfEntity(User.class, false, true)
        .add(AuditEntity.revisionProperty("last_modified_date").between(startDate, endDate))
        .getResultList();

    return results;
  }

}

