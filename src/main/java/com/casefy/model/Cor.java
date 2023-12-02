package com.casefy.model;

public enum Cor {
    AZUL(1, "Azul"),
    VERDE(2, "Verde"),
    AMARELO(3, "Amarelo"),
    ROXO(4, "Roxo"),
    ROSA(5, "Rosa"),
    VERMELHO(6, "Vermelho"),
    LARANJA(7, "Laranja"),
    MARROM(8, "Marrom"),
    CINZA(9, "Cinza"),
    BRANCO(10, "Branco"),
    PRETO(11, "Preto"),
    TRANSPARENTE(12, "Transparente");

    private int id;

    private String label;

    Cor(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static Cor valueOf(Integer id) throws IllegalArgumentException {
        if (id == null)
            return null;
        for (Cor cor : Cor.values()) {
            if (id.equals(cor.getId()))
                return cor;
        }
        throw new IllegalArgumentException("Esse id é inválido:" + id);
    }
}
