package com.janfer.estoque.repositories;

import com.janfer.estoque.domain.entities.ProdutoSaida;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoSaidaRepository extends JpaRepository<ProdutoSaida, Long> {
}