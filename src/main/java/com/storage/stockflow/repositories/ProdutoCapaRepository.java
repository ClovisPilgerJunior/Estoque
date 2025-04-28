package com.storage.stockflow.repositories;

import com.storage.stockflow.domain.entities.ProdutoCapa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoCapaRepository extends JpaRepository<ProdutoCapa, Long> {

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END FROM ProdutoCapa p WHERE p.description = ?1 AND p.id <> ?2")
    boolean existsByDescAndIdNot(String desc, Long id);

    @Query("select (count(p) > 0) from ProdutoCapa p where p.description = ?1")
    boolean existsByDesc(String desc);

    @Query("SELECT p.ativo FROM ProdutoCapa p WHERE p.id = :id")
    Boolean isProdutoAtivoById(@Param("id") Long id);

    @Query("select (count(p) > 0) from ProdutoCapa p where p.fornecedor.id = ?1")
    boolean existsByFornecedor(Long id);


}