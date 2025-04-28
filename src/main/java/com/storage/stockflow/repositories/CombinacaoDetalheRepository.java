package com.storage.stockflow.repositories;

import com.storage.stockflow.domain.entities.CombinacaoDetalhe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

public interface CombinacaoDetalheRepository extends JpaRepository<CombinacaoDetalhe, Long>, RevisionRepository<CombinacaoDetalhe, Long, Long> {
}