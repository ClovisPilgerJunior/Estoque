package com.janfer.estoque.repositories;

import com.janfer.estoque.domain.entities.OrdemAviamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdemAviamentoRepository extends JpaRepository<OrdemAviamento, Long> {
}