package com.janfer.estoque.services;

import com.janfer.estoque.domain.entities.User;
import com.janfer.estoque.domain.enums.Profile;
import com.janfer.estoque.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DBService {

  @Autowired
  UserRepository userRepository;


  public void startDB(){
    Set<Integer> userProfiles = new HashSet<>();
    userProfiles.add(0);
    userProfiles.add(1);

    Set<Integer> userProfiles1 = new HashSet<>();
    userProfiles1.add(2);

    User user = new User(null, "admin", "admin", userProfiles, true);

    User user1 = new User(null, "user", "user", userProfiles1, true);

    User user2 = new User(null, "user2", "user2", userProfiles1, false);

    userRepository.saveAll(List.of(user, user1, user2));
  }
}
