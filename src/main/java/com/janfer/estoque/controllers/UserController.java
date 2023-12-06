package com.janfer.estoque.controllers;

import com.janfer.estoque.domain.dtos.UserGetDTO;
import com.janfer.estoque.domain.dtos.UserPostDTO;
import com.janfer.estoque.domain.entities.User;
import com.janfer.estoque.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('ROLE_ADMIN')")
@RestController
@RequestMapping("/api/user")
public class UserController {

  @Autowired
  UserService userService;

  @GetMapping
  public List<UserGetDTO> findAll(){
    return userService.findAll();
  }

  @GetMapping("/{id}")
  public UserGetDTO findById(@Valid @PathVariable Integer id){
    return userService.findById(id);
  }

  @PostMapping("/cadastrar")
  public User save(@RequestBody UserPostDTO userPostDTO){
    return userService.save(userPostDTO);
  }

  @PutMapping("/atualizar/{id}")
  public User update(@Valid @RequestBody UserPostDTO userPostDTO, @PathVariable @Positive @NotNull Integer id){
    return userService.update(userPostDTO, id);
  }

}
