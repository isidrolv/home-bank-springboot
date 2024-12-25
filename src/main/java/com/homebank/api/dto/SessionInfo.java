package com.homebank.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class SessionInfo {
    private Integer sesionId;      // SESION_ID INTEGER
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fecha;       // FECHA DATE
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime horaInicio;  // HORA_INICIO TIME
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime horaFin;     // HORA_FIN TIME
    private String remoteIp;       // REMOTE_IP VARCHAR(15)
    private Integer usuarioId;     // USUARIO_ID INTEGER
    private String activa;         // ACTIVA CHAR(1)
    private String truncada;       // TRUNCADA CHAR(1)
}

