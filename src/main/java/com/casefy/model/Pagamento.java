package com.casefy.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
@Entity
public class Pagamento extends DefaultEntity {
    @Column(length = 10)
    private String tipo;
    private Float valor;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "pagamento_pix", joinColumns = @JoinColumn(name = "id_pagamento"), inverseJoinColumns = @JoinColumn(name = "id_pix"))
    private List<Pix> pix;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "pagamento_boletoBancario", joinColumns = @JoinColumn(name = "id_pagamento"), inverseJoinColumns = @JoinColumn(name = "id_boletoBancario"))
    private List<BoletoBancario> boletoBancario;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "pagamento_cartaoCredito", joinColumns = @JoinColumn(name = "id_pagamento"), inverseJoinColumns = @JoinColumn(name = "id_cartaoCredito"))
    private List<CartaoCredito> cartaoCredito;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public List<Pix> getPix() {
        return pix;
    }

    public void setPix(List<Pix> pix) {
        this.pix = pix;
    }

    public List<BoletoBancario> getBoletoBancario() {
        return boletoBancario;
    }

    public void setBoletoBancario(List<BoletoBancario> boletoBancario) {
        this.boletoBancario = boletoBancario;
    }

    public List<CartaoCredito> getCartaoCredito() {
        return cartaoCredito;
    }

    public void setCartaoCredito(List<CartaoCredito> cartaoCredito) {
        this.cartaoCredito = cartaoCredito;
    }

}

