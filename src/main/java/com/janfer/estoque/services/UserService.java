package com.janfer.estoque.services;

import com.janfer.estoque.domain.dtos.UserGetDTO;
import com.janfer.estoque.domain.dtos.UserPostDTO;
import com.janfer.estoque.domain.entities.User;
import com.janfer.estoque.domain.mappers.MapStructMapper;
import com.janfer.estoque.repositories.UserRepository;
import com.janfer.estoque.services.exceptions.DataIntegrityViolationException;
import com.janfer.estoque.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.Mapping;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  MapStructMapper mapStructMapper;

  public List<UserGetDTO> findAll(){
    return mapStructMapper.userListAllUser(userRepository.findAll());
  }

  public UserGetDTO findById(Integer id){
    return mapStructMapper.userGetDTOToUser(userRepository.findById(id)
        .orElseThrow(()-> new ObjectNotFoundException("Usuário não encontrado por id")));
  }

  public User save(UserPostDTO userPostDTO){

    if(userRepository.existsByName(userPostDTO.getName())){
      throw new DataIntegrityViolationException("Usuário já registrado no sistema");
    }

    userPostDTO.setAtivo(true);

    return userRepository.save(mapStructMapper.userToUserPostDTO(userPostDTO));
  }

  public User update(UserPostDTO userPostDTO, Integer id){
    if(userRepository.existsByNameAndIdNot(userPostDTO.getName(), id)){
      throw new DataIntegrityViolationException("Usuário já registrado no sistema");
    }

    UserGetDTO userGetDTO = userRepository.findById(id).map(mapStructMapper::userGetDTOToUser)
        .orElseThrow(()-> new ObjectNotFoundException("Usuário não encontrado no sistema"));

    User user = mapStructMapper.userToUserPostDTO(userPostDTO);
    user.setId(userGetDTO.getId());

    return userRepository.save(user);
  }

  public Optional<User> findByName(String nome) {
    return userRepository.findByName(nome);
  }
}
