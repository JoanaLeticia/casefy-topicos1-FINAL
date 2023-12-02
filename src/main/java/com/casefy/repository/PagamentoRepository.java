package com.casefy.repository;

import java.util.List;

import com.casefy.model.Pagamento;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PagamentoRepository implements PanacheRepository<Pagamento>{
    public List<Pagamento> findByTipo(String tipo) {
        return find("UPPER(tipo) LIKE UPPER(?1) ", "%"+tipo+"%").list();
    }
}
