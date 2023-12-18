package com.janfer.estoque.services;

import com.janfer.estoque.domain.entities.User;
import com.janfer.estoque.repositories.UserRepository;
import com.janfer.estoque.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
