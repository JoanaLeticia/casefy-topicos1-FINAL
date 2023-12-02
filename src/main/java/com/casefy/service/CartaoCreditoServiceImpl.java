package com.casefy.service;

import java.util.List;
import java.util.stream.Collectors;

import com.casefy.dto.CartaoCreditoDTO;
import com.casefy.dto.CartaoCreditoResponseDTO;
import com.casefy.model.CartaoCredito;
import com.casefy.repository.CartaoCreditoRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class CartaoCreditoServiceImpl implements CartaoCreditoService {

    @Inject
    CartaoCreditoRepository repository;

    @Override
    @Transactional
    public CartaoCreditoResponseDTO insert(CartaoCreditoDTO dto) {
        CartaoCredito novoCartaoCredito = new CartaoCredito();
        novoCartaoCredito.setBandeira(dto.bandeira());
        novoCartaoCredito.setCodigoSeguranca(dto.codigoSeguranca());
        novoCartaoCredito.setDataVencimento(dto.dataVencimento());
        novoCartaoCredito.setNumeroCartao(dto.numeroCartao());

        repository.persist(novoCartaoCredito);

        return CartaoCreditoResponseDTO.valueOf(novoCartaoCredito);
    }

    @Override
    @Transactional
    public CartaoCreditoResponseDTO update(CartaoCreditoDTO dto, Long id) {

        CartaoCredito boletoExistente = repository.findById(id);
        boletoExistente.setBandeira(dto.bandeira());
        boletoExistente.setCodigoSeguranca(dto.codigoSeguranca());
        boletoExistente.setDataVencimento(dto.dataVencimento());
        boletoExistente.setNumeroCartao(dto.numeroCartao());

        return CartaoCreditoResponseDTO.valueOf(boletoExistente);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id))
            throw new NotFoundException();
    }

    @Override
    public CartaoCreditoResponseDTO findById(Long id) {
        CartaoCredito boleto = repository.findById(id);
        if (boleto == null) {
            throw new EntityNotFoundException("Cartão de Credito não encontrado com ID: " + id);
        }
        return CartaoCreditoResponseDTO.valueOf(boleto);
    }

    @Override
    public List<CartaoCreditoResponseDTO> findByAll() {
        return repository.listAll().stream()
                .map(e -> CartaoCreditoResponseDTO.valueOf(e)).toList();
    }

    @Override
    public List<CartaoCreditoResponseDTO> findByBandeira(String bandeira) {
        List<CartaoCredito> list = repository.findByBandeira(bandeira);
        return list.stream().map(u -> CartaoCreditoResponseDTO.valueOf(u)).collect(Collectors.toList());
    }



}
