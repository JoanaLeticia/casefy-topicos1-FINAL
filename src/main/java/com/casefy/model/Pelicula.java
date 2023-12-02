package com.casefy.model;

import jakarta.persistence.Entity;

@Entity
public class Pelicula extends Produto {
    private String tipo;
    

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
