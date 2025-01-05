package com.homebank.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Prestamo {
    private Integer prestamoId;
    private String numero;
    private Integer clienteId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fecha;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaAutorizacion;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaPago;
    private Double monto;
    private Double intereses;
    private Double capital;
    private Integer plazo;
    private Integer cuotas;
    private Double cuota;
    private Double interesesPagados;
    private Double capitalPagado;
    private Double interesesMoratoriosPagados;
    private Double saldo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaUltimoPago;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaCorte;
    private Double tasaInteres;
    private Double interesMoratorio;
    private Integer planDePagoId;
    private PlanDePago planDePago;
    private String estatus;
    private String tipo;
    private String observaciones;
    private String usuario;
    private String usuarioAutoriza;
    private String usuarioCancela;
    private String usuarioCrea;
    private String usuarioModifica;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaCancelacion;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaCreacion;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaModificacion;
    private boolean activo;
    private boolean cancelado;
    private boolean truncado;
    private boolean pagado;
    private boolean mora;
    private boolean atraso;
    private boolean atraso30;
    private boolean atraso60;
    private boolean atraso90;
    private boolean atraso120;
    private boolean atraso150;
    private boolean atraso180;
    private boolean atraso360;

}