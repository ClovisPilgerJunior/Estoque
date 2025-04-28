package com.storage.stockflow.services;

import com.storage.stockflow.domain.entities.User;
import com.storage.stockflow.repositories.UserRepository;
import com.storage.stockflow.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private final UserRepository userRepository;

  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByName(name);
    if(user.isPresent()){
      return new UserSS(user.get().getId(), user.get().getName(), user.get().getPassword(), user.get().getProfiles());
    }
    throw new UsernameNotFoundException("User Not Found with name: " + name);
  }


}
