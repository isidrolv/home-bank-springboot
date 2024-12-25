package com.homebank.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Data
@NoArgsConstructor
public class LoginRequest {
    @NotNull
    @NotBlank(message = "El nombre de usuario no puede estar vacío.")
    @Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres.")
    private String username; // USERNAME VARCHAR(32)
    @NotNull
    @NotBlank(message = "La contraseña no puede estar vacía.")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres.")
    private String passwd;   // PASSWD_ VARCHAR(32)
    private String remoteIp; // REMOTE_IP VARCHAR(15)
}