package com.janfer.estoque.repositories;

import com.janfer.estoque.domain.entities.OrdemCompra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdemCompraRepository extends JpaRepository<OrdemCompra, Long> {

}