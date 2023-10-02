package com.janfer.estoque.services;

import com.janfer.estoque.domain.entities.Fornecedor;
import com.janfer.estoque.repositories.FornecedorRepository;
import com.janfer.estoque.repositories.ProdutoCapaRepository;
import com.janfer.estoque.services.exceptions.DataIntegrityViolationException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FornecedorService {

    @Autowired
    FornecedorRepository fornecedorRepository;

    @Autowired
    ProdutoCapaRepository produtoCapaRepository;
    @Transactional
    public List<Fornecedor> findAll(){
        return fornecedorRepository.findAll();
    }

    public Fornecedor save(Fornecedor fornecedor){
        return fornecedorRepository.save(fornecedor);
    }

    @Transactional
    public void delete(Fornecedor fornecedor){

        if(produtoCapaRepository.existsById(fornecedor.getId())){
            throw new DataIntegrityViolationException("Não é possível excluir um fornecedor com produto existente");
        }

        fornecedorRepository.delete(fornecedor);
    }

    public Optional<Fornecedor> findById(Long id) {
        return fornecedorRepository.findById(id);
    }

    public boolean existsByEmpresa(String empresa){
        return fornecedorRepository.existsByEmpresa(empresa);
    }
}
