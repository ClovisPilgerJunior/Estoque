package com.janfer.estoque.repositories;

import com.janfer.estoque.domain.entities.Combinacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CombinacaoRepository extends JpaRepository<Combinacao, Long> {
}