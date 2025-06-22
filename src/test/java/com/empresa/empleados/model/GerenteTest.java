package com.empresa.empleados.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GerenteTest {
    private Gerente gerente;

    @BeforeEach
    void setUp() {
        gerente = new Gerente("Ana Sofía", "Gómez", "Martínez", "1234-5678", 5000.0, 2, 3);
    }

    @Test
    void testCalcularSalarioNetoCasoNormal() {
        assertEquals(6356.25, gerente.calcularSalarioNeto(), 0.01, "El salario neto debe ser $6356.25");
    }


    @Test
    void testCalcularSalarioNetoSueldoBaseCero() {
        gerente = new Gerente("Carlos", "Gómez", "54321", 0.0, 1, 1);
        assertEquals(423.75, gerente.calcularSalarioNeto(), 0.01, "El salario neto debe ser $423.75 con sueldo base cero");
    }

}