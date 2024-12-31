package com.homebank.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.homebank.api.entity.Amortizacion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanDePago {
    private Integer id;
    private Double monto;
    private Integer plazo;
    private Integer frecuencia;
    private Double tasa;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaInicio;
    private List<Amortizacion> tablaAmortizacion;

}