package com.storage.stockflow.domain.audit;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@RevisionEntity(value = EntityRevisionListener.class)
public class Revisao {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "revisao_Sequence")
  @SequenceGenerator(name = "revisao_Sequence", sequenceName = "REVISAO_SEQ", allocationSize = 1)
  @RevisionNumber
  private Long revisaoId;

  @RevisionTimestamp
  private Date revisaoData;

  @Column
  private String ip;

  @Column
  private String usuario;

}