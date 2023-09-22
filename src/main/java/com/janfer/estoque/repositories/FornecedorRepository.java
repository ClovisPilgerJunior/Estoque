package com.janfer.estoque.repositories;

import com.janfer.estoque.domain.entities.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

}