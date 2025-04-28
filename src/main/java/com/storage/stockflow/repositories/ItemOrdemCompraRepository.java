package com.storage.stockflow.repositories;

import com.storage.stockflow.domain.entities.ItemOrdemCompra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemOrdemCompraRepository extends JpaRepository<ItemOrdemCompra, Long> {
}