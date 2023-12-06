package com.janfer.estoque.repositories;

import com.janfer.estoque.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  boolean existsByName(String name);
  Optional<User> findByName(String name);
  boolean existsByNameAndIdNot(String name, Integer id);
  @Query("SELECT u.ativo FROM User u WHERE u.name = :name")
  boolean isUsersAtivoByName(@Param("name") String name);
  boolean existsByNameAndAndPasswordNot(String name, String password);


}