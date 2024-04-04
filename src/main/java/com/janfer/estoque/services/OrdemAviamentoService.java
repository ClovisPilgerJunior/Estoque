package com.janfer.estoque.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.janfer.estoque.domain.dtos.*;
import com.janfer.estoque.domain.entities.Combinacao;
import com.janfer.estoque.domain.entities.CombinacaoDetalhe;
import com.janfer.estoque.domain.entities.ItemOrdemAviamento;
import com.janfer.estoque.domain.entities.OrdemAviamento;
import com.janfer.estoque.domain.mappers.MapStructMapper;
import com.janfer.estoque.repositories.CombinacaoDetalheRepository;
import com.janfer.estoque.repositories.CombinacaoRepository;
import com.janfer.estoque.repositories.ItemOrdemAviamentoRepository;
import com.janfer.estoque.repositories.OrdemAviamentoRepository;
import com.janfer.estoque.services.exceptions.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrdemAviamentoService {

    @Autowired
    private OrdemAviamentoRepository ordemAviamentoRepository;

    @Autowired
    private ItemOrdemAviamentoRepository itemOrdemAviamentoRepository;

    @Autowired
    private CombinacaoRepository combinacaoRepository;

    @Autowired
    private CombinacaoDetalheRepository combinacaoDetalheRepository;

    @Autowired
    private MapStructMapper mapStructMapper;

    @Transactional
    public OrdemAviamentoGetDTO createOrdemAviamento(OrdemAviamentoPostDTO ordemAviamentoPostDTO) {
        // Mapear o DTO para a entidade OrdemAviamento
        OrdemAviamento ordemAviamento = mapStructMapper.ordemAviamentoPostDTOToOrdemAviamento(ordemAviamentoPostDTO);

        // Salvar a ordem de aviamento primeiro para obter um ID gerado
        OrdemAviamento ordemAviamentoSalva = ordemAviamentoRepository.save(ordemAviamento);

        // Mapear e salvar os itens da ordem de aviamento
        for (ItemOrdemAviamentoPostDTO itemDTO : ordemAviamentoPostDTO.getItemOrdemAviamento()) {
            ItemOrdemAviamento item = mapStructMapper.itemOrdemAviamentoPostDTOToItemOrdemAviamento(itemDTO);
            item.setOrdemAviamento(ordemAviamentoSalva); // Associar o item à ordem de aviamento salva
            itemOrdemAviamentoRepository.save(item); // Salvar o item diretamente
        }

        // Mapear e salvar as combinações da ordem de aviamento
        for (CombinacaoPostDTO combinacaoDTO : ordemAviamentoPostDTO.getCombinacoes()) {
            Combinacao combinacao = mapStructMapper.combinacaoPostDTOToCombinacao(combinacaoDTO);
            combinacao.setOrdemAviamento(ordemAviamentoSalva); // Associar a combinação à ordem de aviamento salva
            Combinacao combinacaoSalva = combinacaoRepository.save(combinacao);

            // Converter e salvar os detalhes da combinação
            for (CombinacaoDetalhePostDTO detalheDTO : combinacaoDTO.getCombinacoesDetalhes()) {
                CombinacaoDetalhe detalhe = mapStructMapper.combinacaoDetalhePostDTOToCombinacaoDetalhe(detalheDTO);
                detalhe.setCombinacao(combinacaoSalva); // Associar o detalhe à combinação salva
                combinacaoDetalheRepository.save(detalhe);
            }
        }

        // Retornar o DTO atualizado com os IDs gerados
        return mapStructMapper.toOrdemAviamentoGetEntity(ordemAviamentoSalva);
    }



    public List<OrdemAviamentoGetDTO> getAllOrdemAviamentos() {
        List<OrdemAviamento> ordemAviamentos = ordemAviamentoRepository.findAll();
        return mapStructMapper.toListOrdemAviamentoGetEntity(ordemAviamentos);
    }

    public Optional<OrdemAviamentoGetDTO> getOrdemAviamentoById(Long id) {
        Optional<OrdemAviamento> ordemAviamento = ordemAviamentoRepository.findById(id);
        return ordemAviamento.map(mapStructMapper::toOrdemAviamentoGetEntity);
    }

    public OrdemAviamentoGetDTO updateOrdemAviamento(Long id, OrdemAviamentoPostDTO ordemAviamentoPostDTO) {
        Optional<OrdemAviamento> ordemAviamentoOptional = ordemAviamentoRepository.findById(id);
        if (ordemAviamentoOptional.isPresent()) {
            OrdemAviamento ordemAviamento = ordemAviamentoOptional.get();
            // Atualize os campos necessários aqui
            OrdemAviamento updatedOrdemAviamento = ordemAviamentoRepository.save(ordemAviamento);
            return mapStructMapper.toOrdemAviamentoGetEntity(updatedOrdemAviamento);
        } else {
            throw new RuntimeException("OrdemAviamento não encontrada com o id: " + id);
        }
    }

}
