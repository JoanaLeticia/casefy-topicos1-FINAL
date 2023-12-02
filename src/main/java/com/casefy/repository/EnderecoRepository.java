package com.casefy.repository;

import java.util.List;

import com.casefy.model.Endereco;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EnderecoRepository implements PanacheRepository<Endereco> {
    public List<Endereco> findByCep(String cep) {
        return find("UPPER(cep) LIKE UPPER(?1) ", "%" + cep + "%").list();
    }
}
