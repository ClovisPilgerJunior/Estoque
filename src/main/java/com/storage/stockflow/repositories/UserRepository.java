package com.storage.stockflow.repositories;

import com.storage.stockflow.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  boolean existsByName(String name);
  Optional<User> findByName(String name);
  boolean existsByNameAndIdNot(String name, Integer id);
  @Query("SELECT u.ativo FROM User u WHERE u.name = :name")
  boolean isUsersAtivoByName(@Param("name") String name);
  boolean existsByNameAndAndPasswordNot(String name, String password);

  @Query(value = "SELECT u1_0.*, r1_0.* FROM users_aud u1_0 JOIN revisao r4_0 ON r4_0.revisao_id = u1_0.rev JOIN revisao r1_0 ON r1_0.revisao_id = u1_0.rev WHERE r4_0.revisao_data BETWEEN :startDate AND :endDate AND u1_0.rev = r1_0.revisao_id ORDER BY u1_0.rev", nativeQuery = true)
  List<Object[]> findUsersAudByRevisionDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}