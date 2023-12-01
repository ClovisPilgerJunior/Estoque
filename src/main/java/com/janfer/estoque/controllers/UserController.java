package com.janfer.estoque.controllers;

import com.janfer.estoque.domain.dtos.UserGetDTO;
import com.janfer.estoque.domain.entities.User;
import com.janfer.estoque.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

  @Autowired
  UserService userService;

  @GetMapping
  public List<UserGetDTO> findAll(){
    return userService.findAll();
  }

}
