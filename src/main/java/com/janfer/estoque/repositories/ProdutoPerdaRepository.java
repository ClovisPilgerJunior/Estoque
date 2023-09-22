package com.janfer.estoque.repositories;

import com.janfer.estoque.domain.entities.ProdutoPerda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoPerdaRepository extends JpaRepository<ProdutoPerda, Long> {
}