package com.homebank.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Amortizacion {
    private Integer id;
    private Double monto;
    private Integer plazo;
    private Integer frecuencia;
    private Double tasa;
    private Integer numPago;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fecha;
    private Double montoPago;
    private Double tasaMensual;
    private Double intereses;
    private Double amortizacion;
    private Double capitalMasIntereses;
    private Double saldoInsoluto;
    private Double derechosDeudor;
    private Double dac;
    private Double dad;

}