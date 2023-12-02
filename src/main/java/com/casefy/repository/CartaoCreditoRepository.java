package com.casefy.repository;

import java.util.List;

import com.casefy.model.CartaoCredito;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CartaoCreditoRepository implements PanacheRepository<CartaoCredito> {
    public List<CartaoCredito> findByBandeira(String bandeira) {
        return find("UPPER(bandeira) LIKE UPPER(?1) ", "%" + bandeira + "%").list();
    }
}
