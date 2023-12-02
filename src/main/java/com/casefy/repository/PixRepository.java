package com.casefy.repository;

import java.util.List;

import com.casefy.model.Pix;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PixRepository implements PanacheRepository<Pix> {
    public List<Pix> findByChave(String chavePix) {
        return find("UPPER(chavePix) LIKE UPPER(?1) ", "%" + chavePix + "%").list();
    }
}
