package com.casefy.repository;

import java.util.List;

import com.casefy.model.Cliente;
import com.casefy.model.Usuario;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.NoResultException;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente>{
    public List<Cliente> findByNome(String nome) {
        return find("UPPER(nome) LIKE UPPER(?1) ", "%"+nome+"%").list();
    }
    public Cliente findByEmail(String login) {
        try {
            return find("login = ?1", login).singleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Usuario findByLogin(String login) {
        try {
            return find("login = ?1 ", login ).singleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
        
    }
}
