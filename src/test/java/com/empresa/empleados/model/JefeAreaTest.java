package com.empresa.empleados.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class JefeAreaTest {
    private JefeArea jefe;

    @BeforeEach
    void setUp() {
        jefe = new JefeArea(
            "María Elena", "Cruz", "Sánchez",
            "Colonia Escalón, San Salvador", LocalDate.of(1980, 5, 10), 'F',
            "2345-6789", "maria.cruz@empresa.com",
            "Operaciones", "Operativa", 10, true
        );
        jefe.setSalarioBase(3000.0); // Añadido manualmente, ya que no está en el constructor
        jefe.setPresupuestoAnualACargo(500000.0);
        jefe.setMetasAlcanzadas(8);
        jefe.setTotalMetas(10);
        jefe.agregarSubArea("Producción");
    }

    @Test
    void testCalcularSalarioNetoCasoNormal() {
        // Sueldo base: $3000
        // Bonificación: (5% * 8 metas * $3000) + (0.1% * $500000) = $1200 + $500 = $1700
        // Sueldo bruto: $3000 + $1700 = $4700
        // Descuentos: ISSS (7.5% * $4700) + AFP (7.75% * $4700) + Renta (10% * $4700) = $352.50 + $364.25 + $470 = $1186.75
        // Sueldo neto: $4700 - $1186.75 = $3513.25
        assertEquals(3513.25, jefe.calcularSalarioNeto(), 0.01, "El salario neto debe ser $3513.25");
    }


    @Test
    void testCalcularSalarioNetoSueldoBaseCero() {
        jefe.setSalarioBase(0.0);
        // Sueldo base: $0
        // Bonificación: (5% * 8 metas * $0) + (0.1% * $500000) = $0 + $500 = $500
        // Descuentos: ISSS (7.5% * $500) + AFP (7.75% * $500) + Renta (10% * $500) = $37.50 + $38.75 + $50 = $126.25
        // Sueldo neto: $500 - $126.25 = $373.75
        assertEquals(373.75, jefe.calcularSalarioNeto(), 0.01, "El salario neto debe ser $373.75 con sueldo base cero");
    }



    @Test
    void testGetNombreCompleto() {
        assertEquals("María Elena Cruz Sánchez", jefe.getNombreCompleto(), "El nombre completo debe ser correcto");
    }

    
}