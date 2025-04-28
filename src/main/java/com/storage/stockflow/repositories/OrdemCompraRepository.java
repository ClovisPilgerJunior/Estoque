package com.storage.stockflow.repositories;

import com.storage.stockflow.domain.entities.OrdemCompra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdemCompraRepository extends JpaRepository<OrdemCompra, Long> {

}