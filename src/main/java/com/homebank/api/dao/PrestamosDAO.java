package com.homebank.api.dao;

import com.homebank.api.dto.Prestamo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PrestamosDAO {
    String sql = "SELECT r.PRESTAMO_ID, r.NUMERO, r.CLIENTE_ID, r.FECHA, r.FECHA_AUTORIZACION,\n" +
            "    r.FECHA_PAGO, r.MONTO, r.INTERESES, r.CAPITAL, r.PLAZO, r.CUOTAS, r.CUOTA,\n" +
            "    r.INTERESES_PAGADOS, r.CAPITAL_PAGADO, r.INTERESES_MORATORIOS_PAGADOS,\n" +
            "    r.SALDO, r.FECHA_ULTIMO_PAGO, r.FECHA_CORTE, r.TASA_INTERES,\n" +
            "    r.INTERES_MORATORIO, r.PLAN_DE_PAGO_ID, r.ESTATUS, r.TIPO, r.OBSERVACIONES,\n" +
            "    r.USUARIO, r.USUARIO_AUTORIZA, r.USUARIO_CANCELA, r.USUARIO_CREA,\n" +
            "    r.USUARIO_MODIFICA, r.FECHA_CANCELACION, r.FECHA_CREACION,\n" +
            "    r.FECHA_MODIFICACION, r.ACTIVO, r.CANCELADO, r.TRUNCADO, r.PAGADO, r.MORA,\n" +
            "    r.ATRASO, r.ATRASO_30, r.ATRASO_60, r.ATRASO_90, r.ATRASO_120, r.ATRASO_150,\n" +
            "    r.ATRASO_180, r.ATRASO_360\n" +
            "FROM PRESTAMOS r \n";
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Prestamo> prestamoRowMapper = (rs, rowNum) -> Prestamo.builder()
            .prestamoId(rs.getInt("PRESTAMO_ID"))
            .numero(rs.getString("NUMERO"))
            .clienteId(rs.getInt("CLIENTE_ID"))
            .fechaAutorizacion(rs.getDate("FECHA_AUTORIZACION") != null ? rs.getDate("FECHA_AUTORIZACION").toLocalDate() : null)
            .fechaPago(rs.getDate("FECHA_PAGO") != null ? rs.getDate("FECHA_PAGO").toLocalDate() : null)
            .monto(rs.getDouble("MONTO"))
            .intereses(rs.getDouble("INTERESES"))
            .capital(rs.getDouble("CAPITAL")) // TODO: Completar este rowmapper y refactorizar
            .plazo(rs.getInt("PLAZO"))
            .cuotas(rs.getInt("CUOTAS"))
            .cuota(rs.getDouble("CUOTA"))
            .interesesPagados(rs.getDouble("INTERESES_PAGADOS"))
            .capitalPagado(rs.getDouble("CAPITAL_PAGADO"))
            .interesesMoratoriosPagados(rs.getDouble("INTERESES_MORATORIOS_PAGADOS"))
            .saldo(rs.getDouble("SALDO"))
            .fechaUltimoPago(rs.getDate("FECHA_ULTIMO_PAGO") !=null ? rs.getDate("FECHA_ULTIMO_PAGO").toLocalDate() : null)
            .fechaCorte(rs.getDate("FECHA_CORTE") !=null ? rs.getDate("FECHA_CORTE").toLocalDate() : null)
            .tasaInteres(rs.getDouble("TASA_INTERES"))
            .interesMoratorio(rs.getDouble("INTERES_MORATORIO"))
            .planDePagoId(rs.getInt("PLAN_DE_PAGO_ID"))
            .activo(true).cancelado(false).truncado(false).pagado(false).mora(false).atraso(false)
            .atraso30(false).atraso60(false).atraso90(false).atraso120(false)
            .atraso150(false).atraso180(false).atraso360(false)
            .build();

    public PrestamosDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Prestamo> findAllByClienteId(Long clienteId) {
        return jdbcTemplate.query(sql + "WHERE r.CLIENTE_ID = ?", prestamoRowMapper, clienteId);
    }

    public List<Prestamo> findByPrestamoId(Long prestamoId) {
        return jdbcTemplate.query(sql + "WHERE r.PRESTAMO_ID = ?", prestamoRowMapper, prestamoId);
    }

    public List<Prestamo> findAll() {
        return jdbcTemplate.query(sql, prestamoRowMapper);
    }

}
