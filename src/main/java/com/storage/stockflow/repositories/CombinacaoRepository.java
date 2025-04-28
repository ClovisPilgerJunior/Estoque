package com.storage.stockflow.repositories;

import com.storage.stockflow.domain.entities.Combinacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CombinacaoRepository extends JpaRepository<Combinacao, Long> {
    List<Combinacao> findByOrdemAviamentoId(Long id);
}