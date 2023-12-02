package com.casefy.repository;

import java.util.List;

import com.casefy.model.Telefone;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TelefoneRepository implements PanacheRepository<Telefone> {
    public List<Telefone> findByNumero(String numero) {
        return find("UPPER(numero) LIKE UPPER(?1) ", "%" + numero + "%").list();
    }
}
