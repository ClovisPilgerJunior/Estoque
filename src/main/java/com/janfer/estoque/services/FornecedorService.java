package com.janfer.estoque.services;

import com.janfer.estoque.domain.entities.Fornecedor;
import com.janfer.estoque.repositories.FornecedorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FornecedorService {

    @Autowired
    FornecedorRepository fornecedorRepository;

    @Transactional
    public List<Fornecedor> findAll(){
        return fornecedorRepository.findAll();
    }

    public Fornecedor save(Fornecedor fornecedor){
        return fornecedorRepository.save(fornecedor);
    }

    @Transactional
    public void delete(Fornecedor fornecedor){
        fornecedorRepository.delete(fornecedor);
    }

    public Optional<Fornecedor> findById(Long id) {
        return fornecedorRepository.findById(id);
    }

    public boolean existsByEmpresa(String empresa){
        return fornecedorRepository.existsByEmpresa(empresa);
    }
}
