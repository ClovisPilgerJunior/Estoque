package com.storage.stockflow.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

@Repository
public class AuditRepository {

  @PersistenceContext
  private EntityManager entityManager;

  private AuditReader getAuditReader() {
    return AuditReaderFactory.get(entityManager);
  }

  public <T> List<T> getRevisions(final Class<T> tClass, final String prop, final Object propValue) {
    if (Objects.isNull(tClass) || !StringUtils.hasText(prop) || Objects.isNull(propValue)) {
      throw new IllegalArgumentException("Invalid params.");
    }

    try {
      return getAuditReader().createQuery()
          .forRevisionsOfEntity(tClass, true, false)
          .add(AuditEntity.property(prop).eq(propValue))
          .getResultList();
    } finally {
      entityManager.close();
    }
  }
}