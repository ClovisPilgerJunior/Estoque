package com.janfer.estoque.repositories;

import com.janfer.estoque.domain.entities.ProdutoCapa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoCapaRepository extends JpaRepository<ProdutoCapa, Long> {

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END FROM ProdutoCapa p WHERE p.desc = ?1 AND p.id <> ?2")
    boolean existsByDescAndIdNot(String desc, Long id);

    @Query("select (count(p) > 0) from ProdutoCapa p where p.desc = ?1")
    boolean existsByDesc(String desc);

    @Query("SELECT p.ativo FROM ProdutoCapa p WHERE p.id = :id")
    Boolean isProdutoAtivoById(@Param("id") Long id);

    @Query("SELECT p FROM ProdutoCapa p WHERE p.ativo = true")
    List<ProdutoCapa> findAllAtivos();

}