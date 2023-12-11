package com.janfer.estoque.domain.audit;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class GenericRevisionRepository<T> {

  @PersistenceContext
  private EntityManager entityManager;

  public List<EntidadeComRevisao<T>> listaRevisoes(Long id, Class<T> tipo) {
    AuditReader auditReader = AuditReaderFactory.get(entityManager);
    List<Number> idsRevisao = auditReader.getRevisions(tipo, id);
    List<EntidadeComRevisao<T>> allRevisions = new ArrayList<>();
    Map<Number, Revisao> revisoes =  auditReader.findRevisions(Revisao.class,new HashSet<Number>(idsRevisao));

    for (Number revisao : idsRevisao) {
      T listaRevisoes = auditReader.find(tipo, id, revisao);
      Revisao r = revisoes.get(revisao);
      auditReader.findRevisions(Revisao.class,new HashSet<Number>(idsRevisao));
      allRevisions.add(new EntidadeComRevisao<>(new Revisao(r.getRevisaoId(), r.getRevisaoData(),r.getIp(),r.getUsuario()), listaRevisoes));
    }
    return allRevisions;
  }
}
