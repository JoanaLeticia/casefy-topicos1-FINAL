package com.casefy.repository;

import java.util.List;

import com.casefy.model.Cidade;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CidadeRepository implements PanacheRepository<Cidade>{
    public List<Cidade> findByNome(String nome) {
        return find("UPPER(nome) LIKE UPPER(?1) ", "%"+nome+"%").list();
    }
}
