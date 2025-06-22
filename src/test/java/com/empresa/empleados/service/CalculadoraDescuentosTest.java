package com.empresa.empleados.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculadoraDescuentosTest {

    private static final double DELTA = 0.01; 

    @Test
    void testCalcularDescuentoISSSCasoNormal() {
        double sueldoBruto = 800.0;
        // ISSS: 7.5% * $800 = $60
        assertEquals(60.0, CalculadoraDescuentos.calcularDescuentoISSS(sueldoBruto), DELTA,
                "El descuento ISSS debe ser $60 para sueldo bruto de $800");
    }

    @Test
    void testCalcularDescuentoISSSTopeMaximo() {
        double sueldoBruto = 2000.0;
        // ISSS: 7.5% * $1000 (tope) = $75
        assertEquals(75.0, CalculadoraDescuentos.calcularDescuentoISSS(sueldoBruto), DELTA,
                "El descuento ISSS debe ser $75 (tope) para sueldo bruto de $2000");
    }

    @Test
    void testCalcularDescuentoISSSSueldoCero() {
        double sueldoBruto = 0.0;
        assertEquals(0.0, CalculadoraDescuentos.calcularDescuentoISSS(sueldoBruto), DELTA,
                "El descuento ISSS debe ser $0 para sueldo bruto de $0");
    }

    @Test
    void testCalcularDescuentoAFPCasoNormal() {
        double sueldoBruto = 1000.0;
        // AFP: 7.75% * $1000 = $77.50
        assertEquals(77.50, CalculadoraDescuentos.calcularDescuentoAFP(sueldoBruto), DELTA,
                "El descuento AFP debe ser $77.50 para sueldo bruto de $1000");
    }

    @Test
    void testCalcularDescuentoAFPTopeMaximo() {
        double sueldoBruto = 10000.0;
        // AFP: 7.75% * $6500 (tope) = $503.75
        assertEquals(503.75, CalculadoraDescuentos.calcularDescuentoAFP(sueldoBruto), DELTA,
                "El descuento AFP debe ser $503.75 (tope) para sueldo bruto de $10000");
    }

    @Test
    void testCalcularDescuentoAFPSueldoNegativo() {
        double sueldoBruto = -500.0;
        assertEquals(0.0, CalculadoraDescuentos.calcularDescuentoAFP(sueldoBruto), DELTA,
                "El descuento AFP debe ser $0 para sueldo bruto negativo");
    }

    @Test
    void testCalcularDescuentoRentaTramo1() {
        double sueldoBruto = 400.0;
        // Tramo 1: <= $472, renta = $0
        assertEquals(0.0, CalculadoraDescuentos.calcularDescuentoRenta(sueldoBruto), DELTA,
                "El descuento de renta debe ser $0 para sueldo bruto de $400 (Tramo 1)");
    }

    @Test
    void testCalcularDescuentoRentaTramo2() {
        double sueldoBruto = 700.0;
        // Tramo 2: ($700 - $472) * 10% = $22.80
        assertEquals(22.80, CalculadoraDescuentos.calcularDescuentoRenta(sueldoBruto), DELTA,
                "El descuento de renta debe ser $22.80 para sueldo bruto de $700 (Tramo 2)");
    }

    @Test
    void testCalcularDescuentoRentaTramo3() {
        double sueldoBruto = 1500.0;
        // Tramo 3: $42.32 + ($1500 - $895.24) * 20% = $42.32 + $120.95 = $163.27
        assertEquals(163.27, CalculadoraDescuentos.calcularDescuentoRenta(sueldoBruto), DELTA,
                "El descuento de renta debe ser $163.27 para sueldo bruto de $1500 (Tramo 3)");
    }

    @Test
    void testCalcularDescuentoRentaTramo4() {
        double sueldoBruto = 3000.0;
        // Tramo 4: $270.90 + ($3000 - $2038.10) * 30% = $270.90 + $288.57 = $559.47
        assertEquals(559.47, CalculadoraDescuentos.calcularDescuentoRenta(sueldoBruto), DELTA,
                "El descuento de renta debe ser $559.47 para sueldo bruto de $3000 (Tramo 4)");
    }

    @Test
    void testCalcularTotalDescuentosCasoNormal() {
        double sueldoBruto = 1000.0;
        // ISSS: 7.5% * $1000 = $75
        // AFP: 7.75% * $1000 = $77.50
        // Renta: ($1000 - $472) * 10% = $52.80
        // Total: $75 + $77.50 + $52.80 = $205.30
        assertEquals(205.30, CalculadoraDescuentos.calcularTotalDescuentos(sueldoBruto), DELTA,
                "El total de descuentos debe ser $205.30 para sueldo bruto de $1000");
    }

    @Test
    void testCalcularSalarioNetoCasoNormal() {
        double sueldoBruto = 1000.0;
        // Total descuentos: $205.30 
        // Neto: $1000 - $205.30 = $794.70
        assertEquals(794.70, CalculadoraDescuentos.calcularSalarioNeto(sueldoBruto), DELTA,
                "El salario neto debe ser $794.70 para sueldo bruto de $1000");
    }

    @Test
    void testObtenerResumenDescuentosCasoNormal() {
        double sueldoBruto = 1500.0;
        CalculadoraDescuentos.ResumenDescuentos resumen = CalculadoraDescuentos.obtenerResumenDescuentos(sueldoBruto);
        // ISSS: 7.5% * $1000 (tope) = $75
        // AFP: 7.75% * $1500 = $116.25
        // Renta: $42.32 + ($1500 - $895.24) * 20% = $163.27
        // Total descuentos: $75 + $116.25 + $163.27 = $354.52
        // Neto: $1500 - $354.52 = $1145.48
        assertEquals(1500.0, resumen.getSueldoBruto(), DELTA, "El sueldo bruto debe ser $1500");
        assertEquals(75.0, resumen.getDescuentoISSS(), DELTA, "El descuento ISSS debe ser $75");
        assertEquals(116.25, resumen.getDescuentoAFP(), DELTA, "El descuento AFP debe ser $116.25");
        assertEquals(163.27, resumen.getDescuentoRenta(), DELTA, "El descuento de renta debe ser $163.27");
        assertEquals(354.52, resumen.getTotalDescuentos(), DELTA, "El total de descuentos debe ser $354.52");
        assertEquals(1145.48, resumen.getSalarioNeto(), DELTA, "El salario neto debe ser $1145.48");
    }

    @Test
    void testEsExentoDeRentaTramo1() {
        double sueldoBruto = 400.0;
        assertTrue(CalculadoraDescuentos.esExentoDeRenta(sueldoBruto),
                "El sueldo de $400 debe estar exento de renta");
    }

    @Test
    void testEsExentoDeRentaTramo2() {
        double sueldoBruto = 600.0;
        assertFalse(CalculadoraDescuentos.esExentoDeRenta(sueldoBruto),
                "El sueldo de $600 no debe estar exento de renta");
    }

    @Test
    void testObtenerTramoRentaTramo1() {
        double sueldoBruto = 400.0;
        assertEquals(1, CalculadoraDescuentos.obtenerTramoRenta(sueldoBruto),
                "El sueldo de $400 debe estar en el Tramo 1");
    }

    @Test
    void testObtenerTramoRentaTramo3() {
        double sueldoBruto = 1500.0;
        assertEquals(3, CalculadoraDescuentos.obtenerTramoRenta(sueldoBruto),
                "El sueldo de $1500 debe estar en el Tramo 3");
    }


    @Test
    void testToStringResumenDescuentos() {
        double sueldoBruto = 1000.0;
        CalculadoraDescuentos.ResumenDescuentos resumen = CalculadoraDescuentos.obtenerResumenDescuentos(sueldoBruto);
        String expected = String.format(
                "Sueldo Bruto: $1000.00\n" +
                "ISSS (7.5%%): $75.00\n" +
                "AFP (7.75%%): $77.50\n" +
                "Renta: $52.80\n" +
                "Total Descuentos: $205.30\n" +
                "Salario Neto: $794.70");
        assertEquals(expected, resumen.toString(), "El resumen debe tener el formato correcto");
    }
}