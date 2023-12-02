package com.casefy.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.casefy.dto.ItemVendaDTO;
import com.casefy.dto.PedidoDTO;
import com.casefy.dto.PedidoResponseDTO;
import com.casefy.model.Capinha;
import com.casefy.model.Cliente;
import com.casefy.model.Endereco;
import com.casefy.model.ItemVenda;
import com.casefy.model.Pagamento;
import com.casefy.model.Pedido;
import com.casefy.repository.CapinhaRepository;
import com.casefy.repository.ClienteRepository;
import com.casefy.repository.EnderecoRepository;
import com.casefy.repository.ItemVendaRepository;
import com.casefy.repository.PagamentoRepository;
import com.casefy.repository.PedidoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class PedidoServiceImpl implements PedidoService {
    @Inject
    CapinhaRepository capinhaRepository;

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    ItemVendaRepository itemVendaRepository;

    @Inject
    PedidoRepository pedidoRepository;

    @Inject
    EnderecoRepository enderecoRepository;

    @Inject
    PagamentoRepository pagamentoRepository;

    @Override
    @Transactional
    public PedidoResponseDTO insert(PedidoDTO dto, String login) {
        Pedido pedido = new Pedido();
        pedido.setDataHora(LocalDateTime.now());

        Double total = 0.0;
        for (ItemVendaDTO itemDTO : dto.itens()) {
            total += (itemDTO.valor() * itemDTO.quantidade());
        }
        pedido.setValorTotal(total);

        pedido.setItens(new ArrayList<ItemVenda>());
        for (ItemVendaDTO itemDTO : dto.itens()) {
            ItemVenda item = new ItemVenda();
            item.setValor(itemDTO.valor());
            item.setQuantidade(itemDTO.quantidade());
            item.setPedido(pedido);
            Capinha capinha = capinhaRepository.findById(itemDTO.idCapinha());
            item.setCapinha(capinha);

            // atualizado o estoque
            capinha.setQuantEstoque(capinha.getQuantEstoque() - item.getQuantidade());

            pedido.getItens().add(item);
        }

        Pagamento pagamento = pagamentoRepository.findById(dto.idPagamento());
        pedido.setPagamento(pagamento);

        Endereco endereco = enderecoRepository.findById(dto.idEndereco());
        pedido.setEndereco(endereco);

        pedido.setCliente((Cliente) clienteRepository.findByLogin(login));

        pedidoRepository.persist(pedido);

        return PedidoResponseDTO.valueOf(pedido);
    }

    @Override
    public PedidoResponseDTO findById(Long id) {
        return PedidoResponseDTO.valueOf(pedidoRepository.findById(id));
    }

    @Override
    public List<PedidoResponseDTO> findByAll() {
        return pedidoRepository.listAll().stream()
            .map(e -> PedidoResponseDTO.valueOf(e)).toList();
    }

    @Override
    public List<PedidoResponseDTO> findByAll(String login) {
        return pedidoRepository.listAll().stream()
            .map(e -> PedidoResponseDTO.valueOf(e)).toList();
    }


    @Override
public void delete(Long id) {
    // Busca o pedido pelo ID
    Pedido pedido = pedidoRepository.findById(id);
    if (pedido == null) {
        throw new NotFoundException();
    }

    // Deleta todos os itens de venda associados ao pedido
    for (ItemVenda item : pedido.getItens()) {
        itemVendaRepository.delete(item);
    }

    // Agora que todos os itens de venda foram deletados, podemos deletar o pedido
    pedidoRepository.delete(pedido);
}
}
