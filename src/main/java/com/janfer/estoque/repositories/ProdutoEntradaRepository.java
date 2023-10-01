package com.janfer.estoque.repositories;

import com.janfer.estoque.domain.entities.ProdutoEntrada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProdutoEntradaRepository extends JpaRepository<ProdutoEntrada, Long> {

    @Query("SELECT COALESCE(SUM(pe.quantidade), 0) FROM ProdutoEntrada pe WHERE pe.produtoCapa.id = :produtoCapaId GROUP BY pe.produtoCapa.id")
    Double calcularSomaEntradas(@Param("produtoCapaId") Long produtoCapaId);

    @Query("SELECT pe.precoCompra FROM ProdutoEntrada pe WHERE pe.produtoCapa.id = :produtoCapaId ORDER BY pe.dataPedido DESC")
    Double recuperarUltimoPrecoCompra(@Param("produtoCapaId") Long produtoCapaId);

}