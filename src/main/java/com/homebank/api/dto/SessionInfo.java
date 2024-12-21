package com.homebank.api.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class SessionInfo {
    private Integer sesionId;      // SESION_ID INTEGER
    private LocalDate fecha;       // FECHA DATE
    private LocalTime horaInicio;  // HORA_INICIO TIME
    private LocalTime horaFin;     // HORA_FIN TIME
    private String remoteIp;       // REMOTE_IP VARCHAR(15)
    private Integer usuarioId;     // USUARIO_ID INTEGER
    private String activa;         // ACTIVA CHAR(1)
    private String truncada;       // TRUNCADA CHAR(1)
}
