package com.empresa.empleados.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class SupervisorTest {
    private Supervisor supervisor;

    @BeforeEach
    void setUp() {
        supervisor = new Supervisor(
            "Ricardo Andrés", "Ramírez", "García",
            "Mejicanos, San Salvador", LocalDate.of(1985, 7, 12), 'M',
            "5678-9012", "ricardo.ramirez@empresa.com",
            "Producción", "Directa", 5, true
        );
        supervisor.setSalarioBase(2000.0); 
        supervisor.setDiasTrabajadosMes(20);
        supervisor.setIncidentesResueltos(18);
        supervisor.setTotalIncidentes(20);
    }

    @Test
    void testCalcularSalarioNetoCasoNormal() {
        // Sueldo base: $2000
        // Bonificación: (18 incidentes * $50) + (20 días * $20) = $900 + $400 = $1300
        // Sueldo bruto: $2000 + $1300 = $3300
        // Descuentos: ISSS (7.5% * $3300) + AFP (7.75% * $3300) + Renta (10% * $3300) = $247.50 + $255.75 + $330 = $833.25
        // Sueldo neto: $3300 - $833.25 = $2466.75
        assertEquals(2466.75, supervisor.calcularSalarioNeto(), 0.01, "El salario neto debe ser $2466.75");
    }


    @Test
    void testCalcularSalarioNetoSueldoBaseCero() {
        supervisor.setSalarioBase(0.0);
        // Sueldo base: $0
        // Bonificación: (18 incidentes * $50) + (20 días * $20) = $900 + $400 = $1300
        // Descuentos: ISSS (7.5% * $1300) + AFP (7.75% * $1300) + Renta (10% * $1300) = $97.50 + $100.75 + $130 = $328.25
        // Sueldo neto: $1300 - $328.25 = $971.75
        assertEquals(971.75, supervisor.calcularSalarioNeto(), 0.01, "El salario neto debe ser $971.75 con sueldo base cero");
    }

    @Test
    void testSetIncidentesResueltosNegativo() {
        assertThrows(IllegalArgumentException.class, () -> supervisor.setIncidentesResueltos(-5),
                "Debe lanzar excepción para incidentes negativos");
    }

    @Test
    void testGetNombreCompleto() {
        assertEquals("Ricardo Andrés Ramírez García", supervisor.getNombreCompleto(), "El nombre completo debe ser correcto");
    }

   
}