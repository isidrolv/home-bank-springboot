package com.homebank.api.dto;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class UserInfo {
    private Integer usuarioId;     // USUARIO_ID INTEGER
    private String username;       // USERNAME VARCHAR(32)
    private String nombre;         // NOMBRE VARCHAR(65)
    private String passwd;         // PASSWD_ VARCHAR(32)
    private String email;          // EMAIL VARCHAR(85)
    private Integer rolDefecto;    // ROL_DEFECTO INTEGER
}