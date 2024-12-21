package com.homebank.api.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("USUARIOS")
public class Usuario {
    @Id
    @Column(name = "USUARIO_ID", nullable = false)
    private Integer usuarioId;

    @Column(name = "USERNAME", nullable = false, unique = true, length = 32)
    private String username;

    @Column(name = "NOMBRE", nullable = false, length = 65)
    private String nombre;

    @Column(name = "PASSWD_", nullable = false, length = 32)
    private String password;

    @Column(name = "EMAIL", nullable = false, unique = true, length = 85)
    private String email;

    @Column(name = "ROL_DEFECTO", unique = true)
    private Integer rolDefecto;

    @Column(name = "ACTIVO", nullable = false, length = 1)
    private String activo = "N";

    @Column(name = "UNIDAD_ID")
    private Integer unidadId;
}
