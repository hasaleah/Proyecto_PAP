package com.empresa.empleados.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase JefeArea que hereda de Empleado
 * Salario fijo de $1,500
 */
public class JefeArea extends Empleado {
    // Atributos específicos del jefe de área
    private String area;
    private List<String> subAreasACargo;
    private int numeroSubordinados;
    private String tipoArea; // "Operativa", "Administrativa", "Comercial", "Técnica"
    private boolean manejaPresupuesto;
    private double presupuestoAnualACargo;
    private int metasAlcanzadas; // Número de metas cumplidas en el período
    private int totalMetas; // Total de metas asignadas
    
    private static final double SUELDO_JEFE_AREA = 1500.00;
    
    public JefeArea(String nombres, String primerApellido, String segundoApellido,
                    String direccion, LocalDate fechaNacimiento, char sexo,
                    String telefono, String email, String area, String tipoArea,
                    int numeroSubordinados, boolean manejaPresupuesto) {
        super(nombres, primerApellido, segundoApellido, direccion,
              fechaNacimiento, sexo, telefono, email, SUELDO_JEFE_AREA);
        this.area = area;
        this.tipoArea = tipoArea;
        this.numeroSubordinados = numeroSubordinados;
        this.manejaPresupuesto = manejaPresupuesto;
        this.subAreasACargo = new ArrayList<>();
        this.presupuestoAnualACargo = 0.0;
        this.metasAlcanzadas = 0;
        this.totalMetas = 0;
    }
    
    public JefeArea(String nombres, String primerApellido, String segundoApellido,
                    String direccion, LocalDate fechaNacimiento, char sexo,
                    String telefono, String email, String area) {
        this(nombres, primerApellido, segundoApellido, direccion, fechaNacimiento,
             sexo, telefono, email, area, "Operativa", 0, false);
    }
    
    // Getters
    public String getArea() { return area; }
    public List<String> getSubAreasACargo() { return subAreasACargo; }
    public int getNumeroSubordinados() { return numeroSubordinados; }
    public String getTipoArea() { return tipoArea; }
    public boolean isManejaPresupuesto() { return manejaPresupuesto; }
    public double getPresupuestoAnualACargo() { return presupuestoAnualACargo; }
    public int getMetasAlcanzadas() { return metasAlcanzadas; }
    public int getTotalMetas() { return totalMetas; }
    
    // Setters
    public void setArea(String area) { this.area = area; }
    public void setNumeroSubordinados(int numeroSubordinados) { this.numeroSubordinados = numeroSubordinados; }
    public void setTipoArea(String tipoArea) { this.tipoArea = tipoArea; }
    public void setManejaPresupuesto(boolean manejaPresupuesto) { this.manejaPresupuesto = manejaPresupuesto; }
    public void setPresupuestoAnualACargo(double presupuestoAnualACargo) { 
        this.presupuestoAnualACargo = presupuestoAnualACargo; 
    }
    public void setMetasAlcanzadas(int metasAlcanzadas) { this.metasAlcanzadas = metasAlcanzadas; }
    public void setTotalMetas(int totalMetas) { this.totalMetas = totalMetas; }
    
    public void agregarSubArea(String nombreSubArea) {
        if (!subAreasACargo.contains(nombreSubArea)) {
            subAreasACargo.add(nombreSubArea);
        }
    }
    
    public void removerSubArea(String nombreSubArea) {
        subAreasACargo.remove(nombreSubArea);
    }
    
    public double calcularPorcentajeCumplimientoMetas() {
        if (totalMetas == 0) return 0.0;
        return ((double) metasAlcanzadas / totalMetas) * 100;
    }
    
    // Bonificaciones
    public double calcularBonificacionLiderazgo() {
        return numeroSubordinados >= 5 ? sueldoBase * 0.03 * (numeroSubordinados / 5) : 0.0;
    }
    
    public double calcularBonificacionPresupuesto() {
        return manejaPresupuesto && presupuestoAnualACargo >= 50000 ? sueldoBase * 0.04 : 0.0;
    }
    
    public double calcularBonificacionMetas() {
        double porcentaje = calcularPorcentajeCumplimientoMetas();
        if (porcentaje >= 80) return sueldoBase * 0.05;
        if (porcentaje >= 60) return sueldoBase * 0.03;
        return 0.0;
    }
    
    public double calcularBonificacionTipoArea() {
        switch (tipoArea.toLowerCase()) {
            case "técnica": return sueldoBase * 0.06;
            case "comercial": return sueldoBase * 0.05;
            default: return 0.0;
        }
    }
    
    @Override
    public double calcularBonificaciones() {
        return calcularBonificacionLiderazgo() +
               calcularBonificacionPresupuesto() +
               calcularBonificacionMetas() +
               calcularBonificacionTipoArea();
    }
    
    @Override
    public String getTipoEmpleado() {
        return "JEFE DE ÁREA";
    }
    
    public String getResumenJefeArea() {
        return String.format("Jefe de Área %s - Área: %s (%s) - Subordinados: %d - Metas: %.1f%%",
                getNombreCompleto(), area, tipoArea, numeroSubordinados, 
                calcularPorcentajeCumplimientoMetas());
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(String.format(
            "Área a cargo: %s\n" +
            "Tipo de área: %s\n" +
            "Número de subordinados: %d\n" +
            "Maneja presupuesto: %s\n" +
            "Presupuesto anual a cargo: $%.2f\n" +
            "Metas cumplidas: %d de %d (%.1f%%)\n" +
            "Bonificación por liderazgo: $%.2f\n" +
            "Bonificación por presupuesto: $%.2f\n" +
            "Bonificación por metas: $%.2f\n" +
            "Bonificación por tipo de área: $%.2f\n",
            area,
            tipoArea,
            numeroSubordinados,
            manejaPresupuesto ? "Sí" : "No",
            presupuestoAnualACargo,
            metasAlcanzadas, totalMetas, calcularPorcentajeCumplimientoMetas(),
            calcularBonificacionLiderazgo(),
            calcularBonificacionPresupuesto(),
            calcularBonificacionMetas(),
            calcularBonificacionTipoArea()
        ));
        
        if (!subAreasACargo.isEmpty()) {
            sb.append("Sub-áreas a cargo: ");
            sb.append(String.join(", ", subAreasACargo));
            sb.append("\n");
        }
        
        return sb.toString();
    }
}