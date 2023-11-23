package com.janfer.estoque.repositories;

import com.janfer.estoque.domain.entities.UnidadeProdutiva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadeProdutivaRepository extends JpaRepository<UnidadeProdutiva, Long> {
  boolean existsByNome(String nome);
  boolean existsByNomeAndIdNot(String nome, Long id);
}