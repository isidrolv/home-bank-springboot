package com.homebank.api.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class LoginResponse {
    private Integer ok;         // OK INTEGER
    private String msg;         // MSG VARCHAR(65)
    private Integer usuarioId;  // USUARIO_ID INTEGER
    private Integer sesionId;   // SESION_ID INTEGER
}