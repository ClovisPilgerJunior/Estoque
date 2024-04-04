package com.janfer.estoque.repositories;

import com.janfer.estoque.domain.entities.CombinacaoDetalhe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

public interface CombinacaoDetalheRepository extends JpaRepository<CombinacaoDetalhe, Long>, RevisionRepository<CombinacaoDetalhe, Long, Long> {
}