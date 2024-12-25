package com.homebank.api.dao;

import com.homebank.api.dto.SessionInfo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Time;
import java.util.Optional;

@Repository
public class SessionDAO {
    public static final String SP_SESSION_S_BY_ID =
            "SELECT p.SESION_ID, p.FECHA, p.HORA_INICIO, p.HORA_FIN, p.REMOTE_IP, p.USUARIO_ID, p.ACTIVA, p.TRUNCADA FROM SESION_S_BY_ID(?)  p;";
    private final JdbcTemplate jdbcTemplate;

    public SessionDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<SessionInfo> sessionInfoRowMapper = (rs, rowNum) -> {
        int sesionId = rs.getInt("SESION_ID");
        Date fecha = rs.getDate("FECHA");
        Time horaInicio = rs.getTime("HORA_INICIO");
        Time horaFin = rs.getTime("HORA_FIN");
        String remoteIp = rs.getString("REMOTE_IP");
        int usuarioId = rs.getInt("USUARIO_ID");
        String activa = rs.getString("ACTIVA");
        String truncada = rs.getString("TRUNCADA");
        return SessionInfo.builder()
                .sesionId(sesionId)
                .fecha(fecha != null ? fecha.toLocalDate() : null)
                .horaInicio(horaInicio != null ? horaInicio.toLocalTime() : null)
                .horaFin(horaFin != null ? horaFin.toLocalTime() : null)
                .remoteIp(remoteIp)
                .usuarioId(usuarioId)
                .activa(activa)
                .truncada(truncada)
                .build();
    };

    public Optional<SessionInfo> getSessionInfo(Integer sessionId) {
        return jdbcTemplate.query(SP_SESSION_S_BY_ID, sessionInfoRowMapper, sessionId)
                .stream()
                .findFirst();
    }

}
