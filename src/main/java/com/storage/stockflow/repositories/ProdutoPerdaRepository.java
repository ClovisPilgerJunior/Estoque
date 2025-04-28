package com.storage.stockflow.repositories;

import com.storage.stockflow.domain.entities.ProdutoPerda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoPerdaRepository extends JpaRepository<ProdutoPerda, Long> {

  @Query("SELECT COALESCE(SUM(pe.quantidade), 0) FROM ProdutoPerda pe WHERE pe.produtoCapa.id = :produtoCapaId GROUP BY pe.produtoCapa.id")
  Double calcularSomaPerdas(@Param("produtoCapaId") Long produtoCapaId);


}