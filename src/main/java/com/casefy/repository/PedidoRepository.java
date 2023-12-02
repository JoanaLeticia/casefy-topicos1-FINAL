package com.casefy.repository;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

import com.casefy.model.Pedido;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class PedidoRepository implements PanacheRepository<Pedido> {
    public List<Pedido> findAll(String login) {
        return find("cliente.login = ?1",  login).list();
    }

    public List<Pedido> findAll(Long idUsuario) {
        return find("cliente.id = ?1", idUsuario).list();
    }
}
