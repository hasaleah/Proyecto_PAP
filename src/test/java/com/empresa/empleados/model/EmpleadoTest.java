package com.empresa.empleados.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class EmpleadoTest {

    @Test
    void testGetNombreCompleto() {
        JefeArea jefe = new JefeArea(
            "María Elena", "Cruz", "Sánchez",
            "Colonia Escalón, San Salvador", LocalDate.of(1980, 5, 10), 'F',
            "2345-6789", "maria.cruz@empresa.com",
            "Operaciones", "Operativa", 10, true
        );
        assertEquals("María Elena Cruz Sánchez", jefe.getNombreCompleto(), "El nombre completo debe ser correcto");
    }

    @Test
    void testSetSalarioBaseNegativo() {
        JefeArea jefe = new JefeArea(
            "María Elena", "Cruz", "Sánchez",
            "Colonia Escalón, San Salvador", LocalDate.of(1980, 5, 10), 'F',
            "2345-6789", "maria.cruz@empresa.com",
            "Operaciones", "Operativa", 10, true
        );
        assertThrows(IllegalArgumentException.class, () -> jefe.setSalarioBase(-1000.0),
                "Debe lanzar excepción para salario base negativo");
    }

    @Test
    void testSetDuiInvalido() {
        JefeArea jefe = new JefeArea(
            "María Elena", "Cruz", "Sánchez",
            "Colonia Escalón, San Salvador", LocalDate.of(1980, 5, 10), 'F',
            "2345-6789", "maria.cruz@empresa.com",
            "Operaciones", "Operativa", 10, true
        );
        assertThrows(IllegalArgumentException.class, () -> jefe.setDui("invalid-dui"),
                "Debe lanzar excepción para DUI inválido");
    }
}