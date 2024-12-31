package com.homebank.api.dao;

import com.homebank.api.dto.LoginRequest;
import com.homebank.api.dto.LoginResponse;
import com.homebank.api.dto.LogoutResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class LoginDAO {
    public static final String SP_LOGIN = "SELECT p.OK, p.MSG, p.USUARIO_ID, p.SESION_ID FROM LOGIN(?, ?, ?) p";
    public static final String SP_LOGOUT = "SELECT p.OK, p.MSG FROM LOGOUT(?) p";
    private final JdbcTemplate jdbcTemplate;

    public LoginDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<LoginResponse> loginResponseRowMapper = (rs, rowNum) ->
            LoginResponse
                    .builder()
                    .ok(rs.getInt(1))
                    .msg(rs.getString(2))
                    .usuarioId(rs.getInt(3))
                    .sesionId(rs.getInt(4))
                    .build();

    public Optional<LoginResponse> login(LoginRequest loginRequest) {
        return jdbcTemplate
                .query(
                        SP_LOGIN,
                        loginResponseRowMapper,
                        loginRequest.getUsername(), loginRequest.getPasswd(), loginRequest.getRemoteIp())
                .stream()
                .findFirst();
    }

    private final RowMapper<LogoutResponse> logoutResponseRowMapper = (rs, rowNum) ->
            LogoutResponse
                    .builder()
                    .ok(rs.getInt(1))
                    .msg(rs.getString(2))
                    .build();

    public Optional<LogoutResponse> logout(Integer sessionId) {
        return jdbcTemplate
                .query(SP_LOGOUT, logoutResponseRowMapper, sessionId)
                .stream()
                .findFirst();
    }

}
