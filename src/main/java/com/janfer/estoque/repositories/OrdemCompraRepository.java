package com.janfer.estoque.repositories;

import com.janfer.estoque.domain.dtos.OrdemCompraDTO;
import com.janfer.estoque.domain.entities.OrdemCompra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrdemCompraRepository extends JpaRepository<OrdemCompra, Long> {

}