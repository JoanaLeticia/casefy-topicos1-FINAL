package com.casefy.repository;

import java.util.List;

import com.casefy.model.Modelo;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ModeloRepository implements PanacheRepository<Modelo> {
    public List<Modelo> findByNome(String nome) {
        return find("UPPER(nome) LIKE UPPER(?1) ", "%"+nome+"%").list();
    }
}
