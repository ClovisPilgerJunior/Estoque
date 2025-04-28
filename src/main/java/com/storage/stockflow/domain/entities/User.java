package com.storage.stockflow.domain.entities;

import com.storage.stockflow.domain.audit.LocalBaseAuditEntity;
import com.storage.stockflow.domain.enums.Profile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Audited
@Table(name = "users")
public class User extends LocalBaseAuditEntity implements Serializable  {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;
  private String name;
  private String password;
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "PROFILES")
  private Set<Integer> profiles = new HashSet<>();
  private boolean ativo;


  public Set<Profile> getProfiles() {
    return profiles.stream().map(Profile::toEnum).collect(Collectors.toSet());
  }

  public void addProfiles(Profile profile) {
    this.profiles.add(profile.getCode());
  }

}
