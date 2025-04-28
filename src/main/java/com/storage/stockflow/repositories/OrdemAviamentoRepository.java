package com.storage.stockflow.repositories;

import com.storage.stockflow.domain.entities.OrdemAviamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdemAviamentoRepository extends JpaRepository<OrdemAviamento, Long> {
}