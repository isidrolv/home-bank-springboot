package com.homebank.api.dao;

import com.homebank.api.dto.LoginRequest;
import com.homebank.api.dto.LoginResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class LoginDAO {
    public static final String SP_LOGIN = "SELECT p.OK, p.MSG, p.USUARIO_ID, p.SESION_ID FROM LOGIN(?, ?, ?) p";
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

}
