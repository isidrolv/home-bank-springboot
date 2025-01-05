package com.homebank.api.controller;

import com.homebank.api.dto.Prestamo;
import com.homebank.api.service.PrestamosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/prestamos")
public class PrestamosController {
    private PrestamosService prestamosService;

    public PrestamosController(PrestamosService prestamosService) {
        this.prestamosService = prestamosService;
    }

    @GetMapping
    public ResponseEntity<List<Prestamo>> getPrestamos(
            @RequestParam(name="prestamoId", required = false) Long prestamoId,
            @RequestParam(name="clienteId", required = false) Long clienteId) {

        if (prestamoId != null) {
            return ResponseEntity.ok(prestamosService.getPrestamosById(prestamoId));
        } else if (clienteId != null) {
            return ResponseEntity.ok(prestamosService.getPrestamosByClienteId(clienteId));
        } else {
            return ResponseEntity.ok(prestamosService.getPrestamos());
        }
    }

}
