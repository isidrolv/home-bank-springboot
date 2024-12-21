package com.homebank.api.dao;

import com.homebank.api.dto.SessionInfo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SessionDAO {
    public static final String SP_SESSION_S_BY_ID =
            "SELECT p.SESION_ID, p.FECHA, p.HORA_INICIO, p.HORA_FIN, p.REMOTE_IP, p.USUARIO_ID, p.ACTIVA, p.TRUNCADA FROM SESION_S_BY_ID(?)  p;";
    private final JdbcTemplate jdbcTemplate;

    public SessionDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<SessionInfo> sessionInfoRowMapper = (rs, rowNum) ->
            SessionInfo.builder()
                    .sesionId(rs.getInt(1))
                    .fecha((rs.getDate(2)).toLocalDate())
                    .horaInicio((rs.getTime(3)).toLocalTime())
                    .horaFin((rs.getTime(4)).toLocalTime())
                    .remoteIp(rs.getString(5))
                    .usuarioId(rs.getInt(6))
                    .activa(rs.getString(7))
                    .truncada(rs.getString(8))
                    .build();

    public Optional<SessionInfo> getSessionInfo(Integer sessionId) {
        return jdbcTemplate.query(SP_SESSION_S_BY_ID, sessionInfoRowMapper, sessionId)
                .stream()
                .findFirst();
    }

}
