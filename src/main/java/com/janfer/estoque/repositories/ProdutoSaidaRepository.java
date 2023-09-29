package com.janfer.estoque.repositories;

import com.janfer.estoque.domain.entities.ProdutoSaida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProdutoSaidaRepository extends JpaRepository<ProdutoSaida, Long> {
}