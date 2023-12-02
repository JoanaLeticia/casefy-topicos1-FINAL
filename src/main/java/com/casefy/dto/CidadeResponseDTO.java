package com.casefy.dto;

import com.casefy.model.Cidade;
import com.casefy.model.Estado;

public record CidadeResponseDTO(
        Long id,
        String nome,
        Estado estado) {
    public static CidadeResponseDTO valueOf(Cidade cidade){
        return new CidadeResponseDTO(cidade.getId(), cidade.getNome(), cidade.getEstado());
    }
}
