package com.casefy.service;

import com.casefy.dto.UsuarioResponseDTO;

public interface JwtService {

    public String generateJwt(UsuarioResponseDTO dto);
    
}
