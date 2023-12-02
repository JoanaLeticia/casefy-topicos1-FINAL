package com.casefy.repository;

import java.util.List;

import com.casefy.model.Administrador;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AdministradorRepository implements PanacheRepository<Administrador>{
    public List<Administrador> findByNome(String nome) {
        return find("UPPER(nome) LIKE UPPER(?1) ", "%"+nome+"%").list();
    }
}
