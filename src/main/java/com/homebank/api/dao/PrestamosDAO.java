package com.homebank.api.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class PrestamosDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> findAll() {
        String sql = "SELECT * FROM prestamos"; // Replace 'prestamos' with your actual table name
        return jdbcTemplate.queryForList(sql);
    }

    public Object findById(Long id) {
        String sql = "SELECT * FROM prestamos WHERE id = ?"; // Replace 'prestamos' with your actual table name and 'id' with the identifier column name
        return jdbcTemplate.queryForMap(sql, id);
    }
}
