package com.casefy.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Modelo extends DefaultEntity {
    @Column(length = 60)
    private String nome;
    @Column(length = 30)
    private String marca;
    @OneToMany(mappedBy = "modelo")
    private List<Capinha> capinhas;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public List<Capinha> getCapinhas() {
        return capinhas;
    }

    public void setCapinhas(List<Capinha> capinhas) {
        this.capinhas = capinhas;
    }

}
