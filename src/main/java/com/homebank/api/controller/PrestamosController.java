package com.homebank.api.controller;

import com.homebank.api.dto.Prestamo;
import com.homebank.api.service.PrestamosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/prestamos")
public class PrestamosController {
    private PrestamosService prestamosService;

    public PrestamosController(PrestamosService prestamosService) {
        this.prestamosService = prestamosService;
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<Prestamo>> getPrestamos() {
        return ResponseEntity.ok(prestamosService.getPrestamos());
    }
}
