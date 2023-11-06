package com.janfer.estoque.services;

import com.janfer.estoque.domain.entities.Fornecedor;
import com.janfer.estoque.domain.mappers.MapStructMapper;
import com.janfer.estoque.repositories.FornecedorRepository;
import com.janfer.estoque.repositories.ProdutoCapaRepository;
import com.janfer.estoque.services.exceptions.DataIntegrityViolationException;
import com.janfer.estoque.services.exceptions.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
public class FornecedorService {

    @Autowired
    FornecedorRepository fornecedorRepository;

    @Autowired
    ProdutoCapaRepository produtoCapaRepository;
    @Autowired
    private MapStructMapper mapStructMapper;

    @Transactional
    public List<Fornecedor> findAll(){
        return fornecedorRepository.findAll();
    }

    public Fornecedor save(Fornecedor fornecedor){
        return fornecedorRepository.save(fornecedor);
    }

    @Transactional
    public void delete(@Positive @NotNull Long id){

        if(produtoCapaRepository.existsByFornecedor(id)) {
            throw new DataIntegrityViolationException("Não é possível excluir um fornecedor com produto existente");
        }
        fornecedorRepository.delete(fornecedorRepository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException("Fornecedor não Encontrado!")));
    }

    public Optional<Fornecedor> findById(Long id) {
        return fornecedorRepository.findById(id);
    }

    public boolean existByEmpresaAndIdNot(String empresa, Long id){
       return fornecedorRepository.existsByEmpresaAndIdNot(empresa, id);
    }

    public boolean existByEmpresa(String empresa){
        return fornecedorRepository.existsByEmpresa(empresa);
    }

    public boolean existByEmail(String email) {
        return  fornecedorRepository.existsByEmail(email);
    }

  public boolean existByEmailAndIdNot(String email, Long id) {
        return fornecedorRepository.existsByEmailAndIdNot(email, id);
  }
}
