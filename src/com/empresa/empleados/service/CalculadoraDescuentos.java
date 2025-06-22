package com.empresa.empleados.service;

/**
 * Clase utilitaria para el cálculo de descuentos de ley en El Salvador
 * Centraliza todos los cálculos de descuentos para mantener consistencia
 */
public class CalculadoraDescuentos {
    
    // Constantes de descuentos de ley
    public static final double TASA_ISSS = 0.075;      // 7.5%
    public static final double TASA_AFP = 0.0775;      // 7.75%
    
    // Constantes para el cálculo de renta
    public static final double RENTA_TRAMO_1_LIMITE = 472.00;
    public static final double RENTA_TRAMO_2_LIMITE = 895.24;
    public static final double RENTA_TRAMO_3_LIMITE = 2038.10;
    
    public static final double RENTA_TRAMO_2_BASE = 42.32;
    public static final double RENTA_TRAMO_3_BASE = 270.90;
    
    public static final double RENTA_TASA_TRAMO_2 = 0.10;   // 10%
    public static final double RENTA_TASA_TRAMO_3 = 0.20;   // 20%
    public static final double RENTA_TASA_TRAMO_4 = 0.30;   // 30%
    
    // Límites máximos de descuentos (topes)
    public static final double TOPE_ISSS = 1000.00 * TASA_ISSS;  // Tope sobre $1,000
    public static final double TOPE_AFP = 6500.00 * TASA_AFP;    // Tope sobre $6,500
    
    /**
     * Constructor privado para evitar instanciación (clase utilitaria)
     */
    private CalculadoraDescuentos() {
        throw new UnsupportedOperationException("Clase utilitaria - no debe ser instanciada");
    }
    
    /**
     * Calcula el descuento de ISSS aplicando el tope correspondiente
     * @param sueldoBruto Sueldo bruto del empleado
     * @return Monto del descuento ISSS
     */
    public static double calcularDescuentoISSS(double sueldoBruto) {
        if (sueldoBruto <= 0) return 0.0;
        
        double descuento = sueldoBruto * TASA_ISSS;
        return Math.min(descuento, TOPE_ISSS); // Aplicar tope máximo
    }
    
    /**
     * Calcula el descuento de AFP aplicando el tope correspondiente
     * @param sueldoBruto Sueldo bruto del empleado
     * @return Monto del descuento AFP
     */
    public static double calcularDescuentoAFP(double sueldoBruto) {
        if (sueldoBruto <= 0) return 0.0;
        
        double descuento = sueldoBruto * TASA_AFP;
        return Math.min(descuento, TOPE_AFP); // Aplicar tope máximo
    }
    
    /**
     * Calcula el descuento de renta según la tabla progresiva de El Salvador
     * @param sueldoBruto Sueldo bruto del empleado
     * @return Monto del descuento de renta
     */
    public static double calcularDescuentoRenta(double sueldoBruto) {
        if (sueldoBruto <= 0) return 0.0;
        
        if (sueldoBruto <= RENTA_TRAMO_1_LIMITE) {
            // Tramo 1: Hasta $472.00 - 0%
            return 0.0;
        } else if (sueldoBruto <= RENTA_TRAMO_2_LIMITE) {
            // Tramo 2: De $472.01 a $895.24 - 10% sobre el exceso de $472.00
            return (sueldoBruto - RENTA_TRAMO_1_LIMITE) * RENTA_TASA_TRAMO_2;
        } else if (sueldoBruto <= RENTA_TRAMO_3_LIMITE) {
            // Tramo 3: De $895.25 a $2,038.10 - $42.32 + 20% sobre el exceso de $895.24
            return RENTA_TRAMO_2_BASE + 
                   ((sueldoBruto - RENTA_TRAMO_2_LIMITE) * RENTA_TASA_TRAMO_3);
        } else {
            // Tramo 4: Más de $2,038.10 - $270.90 + 30% sobre el exceso de $2,038.10
            return RENTA_TRAMO_3_BASE + 
                   ((sueldoBruto - RENTA_TRAMO_3_LIMITE) * RENTA_TASA_TRAMO_4);
        }
    }
    
    /**
     * Calcula el total de descuentos de ley
     * @param sueldoBruto Sueldo bruto del empleado
     * @return Monto total de descuentos
     */
    public static double calcularTotalDescuentos(double sueldoBruto) {
        return calcularDescuentoISSS(sueldoBruto) + 
               calcularDescuentoAFP(sueldoBruto) + 
               calcularDescuentoRenta(sueldoBruto);
    }
    
    /**
     * Calcula el salario neto después de descuentos
     * @param sueldoBruto Sueldo bruto del empleado
     * @return Salario neto
     */
    public static double calcularSalarioNeto(double sueldoBruto) {
        return sueldoBruto - calcularTotalDescuentos(sueldoBruto);
    }
    
    /**
     * Obtiene un resumen detallado de todos los descuentos
     * @param sueldoBruto Sueldo bruto del empleado
     * @return ResumenDescuentos con todos los cálculos
     */
    public static ResumenDescuentos obtenerResumenDescuentos(double sueldoBruto) {
        double isss = calcularDescuentoISSS(sueldoBruto);
        double afp = calcularDescuentoAFP(sueldoBruto);
        double renta = calcularDescuentoRenta(sueldoBruto);
        double total = isss + afp + renta;
        double neto = sueldoBruto - total;
        
        return new ResumenDescuentos(sueldoBruto, isss, afp, renta, total, neto);
    }
    
    /**
     * Verifica si un sueldo está en el rango exento de renta
     * @param sueldoBruto Sueldo bruto a verificar
     * @return true si está exento de renta
     */
    public static boolean esExentoDeRenta(double sueldoBruto) {
        return sueldoBruto <= RENTA_TRAMO_1_LIMITE;
    }
    
    /**
     * Obtiene el tramo de renta al que pertenece un sueldo
     * @param sueldoBruto Sueldo bruto
     * @return Número del tramo (1-4)
     */
    public static int obtenerTramoRenta(double sueldoBruto) {
        if (sueldoBruto <= RENTA_TRAMO_1_LIMITE) return 1;
        if (sueldoBruto <= RENTA_TRAMO_2_LIMITE) return 2;
        if (sueldoBruto <= RENTA_TRAMO_3_LIMITE) return 3;
        return 4;
    }
    
    /**
     * Clase interna para encapsular el resumen de descuentos
     */
    public static class ResumenDescuentos {
        private final double sueldoBruto;
        private final double descuentoISSS;
        private final double descuentoAFP;
        private final double descuentoRenta;
        private final double totalDescuentos;
        private final double salarioNeto;
        
        public ResumenDescuentos(double sueldoBruto, double descuentoISSS, 
                               double descuentoAFP, double descuentoRenta, 
                               double totalDescuentos, double salarioNeto) {
            this.sueldoBruto = sueldoBruto;
            this.descuentoISSS = descuentoISSS;
            this.descuentoAFP = descuentoAFP;
            this.descuentoRenta = descuentoRenta;
            this.totalDescuentos = totalDescuentos;
            this.salarioNeto = salarioNeto;
        }
        
        // Getters
        public double getSueldoBruto() { return sueldoBruto; }
        public double getDescuentoISSS() { return descuentoISSS; }
        public double getDescuentoAFP() { return descuentoAFP; }
        public double getDescuentoRenta() { return descuentoRenta; }
        public double getTotalDescuentos() { return totalDescuentos; }
        public double getSalarioNeto() { return salarioNeto; }
        
        @Override
        public String toString() {
            return String.format(
                "Sueldo Bruto: $%.2f\n" +
                "ISSS (%.1f%%): $%.2f\n" +
                "AFP (%.2f%%): $%.2f\n" +
                "Renta: $%.2f\n" +
                "Total Descuentos: $%.2f\n" +
                "Salario Neto: $%.2f",
                sueldoBruto,
                TASA_ISSS * 100,
                descuentoISSS,
                TASA_AFP * 100,
                descuentoAFP,
                descuentoRenta,
                totalDescuentos,
                salarioNeto
            );
        }
    }
}