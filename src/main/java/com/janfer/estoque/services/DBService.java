package com.janfer.estoque.services;

import com.janfer.estoque.domain.entities.User;
import com.janfer.estoque.domain.enums.Profile;
import com.janfer.estoque.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class DBService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  BCryptPasswordEncoder encoder;

  public void startDB(){
    Set<Integer> userProfiles = new HashSet<>();
    userProfiles.add(0);
    userProfiles.add(1);

    User user = new User(null, "admin", encoder.encode("admin"), userProfiles);
    user.addProfiles(Profile.ROLE_ADMIN);

    userRepository.save(user);
  }
}
