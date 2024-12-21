package com.homebank.api.dao;

import com.homebank.api.dto.UserInfo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDAO {
    public static final String SP_USUARIO_S_BY_ID = "SELECT p.USUARIO_ID, p.USERNAME, p.NOMBRE, p.PASSWD_, p.EMAIL, p.ROL_DEFECTO FROM USUARIO_S_BY_ID(?)  p;";
    private final JdbcTemplate jdbcTemplate;

    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<UserInfo> userInfoRowMapper = (rs, rowNum) ->
            UserInfo.builder()
                    .usuarioId(rs.getInt(1))
                    .username(rs.getString(2))
                    .nombre(rs.getString(3))
                    .passwd(rs.getString(4))
                    .email(rs.getString(5))
                    .rolDefecto(rs.getInt(6))
                    .build();

    public Optional<UserInfo> getUserInfo(Integer usuarioId) {

        return jdbcTemplate
                .query(SP_USUARIO_S_BY_ID, userInfoRowMapper, usuarioId)
                .stream()
                .findFirst();

    }

}
