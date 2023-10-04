package com.janfer.estoque.repositories;

import com.janfer.estoque.domain.entities.ProdutoCapa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProdutoCapaRepository extends JpaRepository<ProdutoCapa, Long> {

    @Query("select (count(p) > 0) from ProdutoCapa p where p.desc = ?1")
    boolean existsByDesc(String desc);

    @Query("SELECT p.ativo FROM ProdutoCapa p WHERE p.id = :id")
    Boolean isProdutoAtivoById(@Param("id") Long id);

    @Query("SELECT p FROM ProdutoCapa p WHERE p.ativo = true")
    List<ProdutoCapa> findAllAtivos();

}