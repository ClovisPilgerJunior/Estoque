package com.janfer.estoque.repositories;

import com.janfer.estoque.domain.entities.ProdutoEntrada;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoEntradaRepository extends JpaRepository<ProdutoEntrada, Long> {
}