package com.janfer.estoque.services;

import com.janfer.estoque.domain.dtos.UserGetDTO;
import com.janfer.estoque.domain.entities.User;
import com.janfer.estoque.domain.mappers.MapStructMapper;
import com.janfer.estoque.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.Mapping;

import java.util.List;

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  MapStructMapper mapStructMapper;

  public List<UserGetDTO> findAll(){
    return mapStructMapper.userListAllUser(userRepository.findAll());
  }

}
