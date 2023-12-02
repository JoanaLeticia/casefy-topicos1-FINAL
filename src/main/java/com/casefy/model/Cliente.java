package com.casefy.model;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;

@Entity
public class Cliente extends Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 12)
    private Date dataNascimento;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "cliente_endereco", joinColumns = @JoinColumn(name = "id_cliente"), inverseJoinColumns = @JoinColumn(name = "id_endereco"))
    private List<Endereco> endereco;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "cliente_telefone", joinColumns = @JoinColumn(name = "id_cliente"), inverseJoinColumns = @JoinColumn(name = "id_telefone"))
    private List<Telefone> telefone;

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public List<Endereco> getEndereco() {
        return endereco != null ? endereco : Collections.emptyList();
    }

    public void setEndereco(List<Endereco> endereco) {
        this.endereco = endereco;
    }

    public List<Telefone> getTelefone() {
        return telefone;
    }

    public void setTelefone(List<Telefone> telefone) {
        this.telefone = telefone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
