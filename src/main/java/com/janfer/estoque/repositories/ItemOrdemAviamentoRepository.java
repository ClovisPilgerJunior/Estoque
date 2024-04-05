package com.janfer.estoque.repositories;

import com.janfer.estoque.domain.entities.ItemOrdemAviamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemOrdemAviamentoRepository extends JpaRepository<ItemOrdemAviamento, Long> {
    List<ItemOrdemAviamento> findByOrdemAviamentoId(Long id);
}