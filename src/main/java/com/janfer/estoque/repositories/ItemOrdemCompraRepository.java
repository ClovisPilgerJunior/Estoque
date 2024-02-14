package com.janfer.estoque.repositories;

import com.janfer.estoque.domain.entities.ItemOrdemCompra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemOrdemCompraRepository extends JpaRepository<ItemOrdemCompra, Long> {
}