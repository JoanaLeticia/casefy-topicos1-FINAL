package com.casefy.dto;

import com.casefy.model.Pix;

public record PixResponseDTO(
        Long id,
        String chavePix) {

    public static PixResponseDTO valueOf(Pix pix) {
        return new PixResponseDTO(
                pix.getId(),
                pix.getChavePix());
    }
}
