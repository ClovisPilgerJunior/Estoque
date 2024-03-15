package com.janfer.estoque.repositories;

import com.janfer.estoque.domain.entities.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

  @Query("select (count(f) > 0) from Fornecedor f where f.empresa = ?1 and f.id <> ?2")
  boolean existsByEmpresaAndIdNot(String empresa, Long id);

  @Query("select (count(f) > 0) from Fornecedor f where f.empresa = ?1")
  boolean existsByEmpresa(String empresa);

  boolean existsByEmail(String email);

  @Query("select (count(f) > 0) from Fornecedor f where f.email = ?1 and f.id <> ?2")
  boolean existsByEmailAndIdNot(String email, Long id);

    Optional<Fornecedor> findByEmpresa(String empresa);
}
