package com.casefy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ItemVenda extends DefaultEntity {

    private Integer quantidade;
    private Double valor;

    @ManyToOne
    @JoinColumn(name = "id_capinha")
    private Capinha capinha;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Capinha getCapinha() {
        return capinha;
    }

    public void setCapinha(Capinha capinha) {
        this.capinha = capinha;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

}

