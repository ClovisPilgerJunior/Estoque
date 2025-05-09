package com.storage.stockflow.domain.audit;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class LocalBaseAuditEntity {

  @CreatedBy
  @Column(name = "created_by", updatable = false)
  private String createdBy;

  @CreatedDate
  @Column(name = "created_date", nullable = false, updatable = false)
  // You can use any date-time object.
  private Instant createDate = Instant.now();

  @Audited
  @LastModifiedBy
  @Column(name = "last_modified_by")
  private String lastModifiedBy;

  @Audited
  @LastModifiedDate
  @Column(name = "last_modified_date", nullable = false)
  // You can use any date-time object.
  private Instant lastModifiedDate = Instant.now();
}
