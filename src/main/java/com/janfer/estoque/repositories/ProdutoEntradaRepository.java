package com.janfer.estoque.repositories;

import com.janfer.estoque.domain.entities.ProdutoEntrada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProdutoEntradaRepository extends JpaRepository<ProdutoEntrada, Long> {

//  @Query("SELECT SUM(pe.quantidade) FROM ProdutoEntrada pe WHERE pe.produtoEntrada.sku = :idProduto")
//  Integer sumQuantidadeByIdProduto(@Param("idProduto") Long idProduto);

}