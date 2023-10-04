package com.janfer.estoque.repositories;

import com.janfer.estoque.domain.entities.ProdutoCapa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProdutoCapaRepository extends JpaRepository<ProdutoCapa, Long> {

    @Query("select (count(p) > 0) from ProdutoCapa p where p.desc = ?1")
    boolean existsByDesc(String desc);

    @Query("select p from ProdutoCapa p where p.ativo = :ativo")
    ProdutoCapa findByAtivo(@Param("ativo") boolean ativo);


}