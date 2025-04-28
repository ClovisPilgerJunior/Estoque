package com.storage.stockflow.services;

import com.storage.stockflow.domain.dtos.UserGetDTO;
import com.storage.stockflow.domain.dtos.UserPostDTO;
import com.storage.stockflow.domain.entities.User;
import com.storage.stockflow.domain.mappers.MapStructMapper;
import com.storage.stockflow.repositories.UserRepository;
import com.storage.stockflow.services.exceptions.DataIntegrityViolationException;
import com.storage.stockflow.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  MapStructMapper mapStructMapper;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public List<UserGetDTO> findAll(){
    return mapStructMapper.userListAllUser(userRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));
  }

  public UserGetDTO findById(Integer id){
    return mapStructMapper.userGetDTOToUser(userRepository.findById(id)
        .orElseThrow(()-> new ObjectNotFoundException("Usuário não encontrado por id")));
  }

  public User save(UserPostDTO userPostDTO){

    // Validação de unicidade pelo nome
    if(userRepository.existsByName(userPostDTO.getName())){
      throw new DataIntegrityViolationException("Nome de usuário '" + userPostDTO.getName() + "' já registrado no sistema");
    }

    // Mapeia DTO para entidade para criação
    User user = mapStructMapper.userToUserPostDTO(userPostDTO); // Assumindo que isso mapeia DTO para entidade User

    // Configura campos padrão para novo usuário
    user.setId(null); // Garante que é um novo registro
    user.setAtivo(true); // Configura como ativo por padrão no cadastro

    // --- CODIFICAR SENHA: ESSENCIAL PARA NOVOS USUÁRIOS ---
    // A senha deve vir do DTO e ser codificada antes de salvar
    if (userPostDTO.getPassword() != null && !userPostDTO.getPassword().isEmpty()) {
      user.setPassword(passwordEncoder.encode(userPostDTO.getPassword()));
    } else {
      // Lançar erro se a senha estiver faltando no cadastro (frontend deveria validar, mas backend garante)
      throw new DataIntegrityViolationException("A senha é obrigatória para novos usuários.");
    }


    // Salvar a nova entidade User (com a senha codificada)
    return userRepository.save(user);
  }

  public User update(UserPostDTO userPostDTO, Integer id){
    // 1. Buscar o usuário EXISTENTE no banco de dados
    User existingUser = userRepository.findById(id)
            .orElseThrow(()-> new ObjectNotFoundException("Usuário não encontrado no sistema com id: " + id));

    // 2. Verificar conflito de nome, excluindo o próprio usuário atual
    if(userRepository.existsByNameAndIdNot(userPostDTO.getName(), id)){
      throw new DataIntegrityViolationException("Nome de usuário '" + userPostDTO.getName() + "' já registrado no sistema");
    }

    // 3. Atualizar os campos do usuário EXISTENTE com os dados do DTO
    //    Mapear campos exceto a senha
    //    Você pode precisar de um método específico no seu MapStructMapper
    //    que atualize uma entidade existente e ignore a senha, ou fazer manualmente.

    // Exemplo usando MapStruct com método de atualização (precisa configurar no mapper)
    // Assumindo que você tem um método assim: @Mapping(target = "password", ignore = true) void updateFromDto(UserPostDTO dto, @MappingTarget User entity);
    // mapStructMapper.updateFromDto(userPostDTO, existingUser);

    // Exemplo fazendo manualmente (recomendado se não quiser mexer no mapper agora):
    existingUser.setName(userPostDTO.getName());
    existingUser.setProfiles(userPostDTO.getProfiles()); // Assumindo que os perfis do DTO substituem os existentes
    existingUser.setAtivo(userPostDTO.isAtivo());       // Assumindo que o status ativo do DTO substitui o existente


    // --- 4. Atualização CONDICIONAL da Senha ---
    // Verificar se a senha foi fornecida no DTO recebido do frontend
    // (Se o frontend deixou em branco, o DTO terá getPassword() == null ou isEmpty())
    if (userPostDTO.getPassword() != null && !userPostDTO.getPassword().isEmpty()) {
      // Se uma NOVA senha foi fornecida (não é nulo nem vazio), codifique-a e atualize
      existingUser.setPassword(passwordEncoder.encode(userPostDTO.getPassword()));
      System.out.println("DEBUG BACKEND: Senha fornecida na atualização para o usuário " + id + ". Codificando e atualizando.");
    } else {
      // Se a senha NÃO foi fornecida no DTO (é nulo ou vazio),
      // NÃO FAÇA NADA. A senha existente do existingUser (carregada do DB) será mantida.
      System.out.println("DEBUG BACKEND: Senha NÃO fornecida na atualização para o usuário " + id + ". Mantendo senha existente.");
    }


    // 5. Salvar o usuário EXISTENTE e AGORA MODIFICADO
    return userRepository.save(existingUser);
  }

  public Optional<User> findByName(String nome) {
    return userRepository.findByName(nome);
  }
}
