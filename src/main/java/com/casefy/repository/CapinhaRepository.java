package com.casefy.repository;

import java.util.List;

import com.casefy.model.Capinha;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CapinhaRepository implements PanacheRepository<Capinha>{
    public List<Capinha> findByNome(String nome) {
        return find("UPPER(nome) LIKE UPPER(?1) ", "%"+nome+"%").list();
    }
}
