package com.homebank.api.service;

import com.homebank.api.dao.PrestamosDAO;
import com.homebank.api.dto.Prestamo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PrestamosService {

    private final PrestamosDAO prestamosDAO;

    public PrestamosService(PrestamosDAO prestamosRepository) {
        this.prestamosDAO = prestamosRepository;
    }

    public List<Prestamo> getPrestamos() {
        return prestamosDAO.findAll()
                .stream()
                .map(this::mapToPrestamo)
                .collect(Collectors.toList());
    }

    private Prestamo mapToPrestamo(Map<String, Object> stringObjectMap) {
        return Prestamo.builder().activo(true)
                .cancelado(false)
                .truncado(false)
                .pagado(false)
                .mora(false)
                .atraso(false)
                .atraso30(false).atraso60(false)
                .atraso90(false).atraso120(false).atraso150(false)
                .atraso180(false).atraso360(false)
                .build();
    }

}
