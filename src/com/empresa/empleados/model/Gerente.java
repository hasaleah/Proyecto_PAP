package com.empresa.empleados.model;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

/**
 * Clase Gerente que hereda de Empleado
 * Salario fijo de $5,000
 */
public class Gerente extends Empleado {
    // Atributos específicos del gerente
    private String departamento;
    private List<String> equiposACargo;
    private int numeroEmpleadosACargo;
    private boolean tieneAutoEmpresa;
    
    // Sueldo fijo para gerentes
    private static final double SUELDO_GERENTE = 5000.00;
    
    /**
     * Constructor de la clase Gerente
     */
    public Gerente(String nombres, String primerApellido, String segundoApellido,
                   String direccion, LocalDate fechaNacimiento, char sexo,
                   String telefono, String email, String departamento,
                   int numeroEmpleadosACargo, boolean tieneAutoEmpresa) {
        // Llamamos al constructor de la clase padre con el sueldo fijo
        super(nombres, primerApellido, segundoApellido, direccion, 
              fechaNacimiento, sexo, telefono, email, SUELDO_GERENTE);
        
        this.departamento = departamento;
        this.numeroEmpleadosACargo = numeroEmpleadosACargo;
        this.tieneAutoEmpresa = tieneAutoEmpresa;
        this.equiposACargo = new ArrayList<>();
    }
    
    /**
     * Constructor alternativo sin equipos específicos
     */
    public Gerente(String nombres, String primerApellido, String segundoApellido,
                   String direccion, LocalDate fechaNacimiento, char sexo,
                   String telefono, String email, String departamento) {
        this(nombres, primerApellido, segundoApellido, direccion, fechaNacimiento,
             sexo, telefono, email, departamento, 0, false);
    }
    
    // Getters específicos del gerente
    public String getDepartamento() { return departamento; }
    public List<String> getEquiposACargo() { return equiposACargo; }
    public int getNumeroEmpleadosACargo() { return numeroEmpleadosACargo; }
    public boolean isTieneAutoEmpresa() { return tieneAutoEmpresa; }
    
    // Setters específicos del gerente
    public void setDepartamento(String departamento) { this.departamento = departamento; }
    public void setNumeroEmpleadosACargo(int numeroEmpleadosACargo) { 
        this.numeroEmpleadosACargo = numeroEmpleadosACargo; 
    }
    public void setTieneAutoEmpresa(boolean tieneAutoEmpresa) { 
        this.tieneAutoEmpresa = tieneAutoEmpresa; 
    }
    
    /**
     * Agrega un equipo a la lista de equipos a cargo
     */
    public void agregarEquipoACargo(String nombreEquipo) {
        if (!equiposACargo.contains(nombreEquipo)) {
            equiposACargo.add(nombreEquipo);
        }
    }
    
    /**
     * Remueve un equipo de la lista de equipos a cargo
     */
    public void removerEquipoACargo(String nombreEquipo) {
        equiposACargo.remove(nombreEquipo);
    }
    
    /**
     * Calcula bonificación por gestión (ejemplo: 5% del sueldo base por cada 10 empleados)
     */
    public double calcularBonificacionGestion() {
        if (numeroEmpleadosACargo >= 10) {
            int gruposDeDiez = numeroEmpleadosACargo / 10;
            return sueldoBase * 0.05 * gruposDeDiez;
        }
        return 0.0;
    }
    
    /**
     * Calcula bonificación por auto de empresa
     */
    public double calcularBonificacionAuto() {
        return tieneAutoEmpresa ? 200.00 : 0.0;
    }
    
    /**
     * Calcula el sueldo total incluyendo bonificaciones
     * Para efectos de descuentos, se usa el sueldo base + bonificaciones
     */
    public double calcularSueldoConBonificaciones() {
        return sueldoBase + calcularBonificacionGestion() + calcularBonificacionAuto();
    }
    
    /**
     * Override del método calcularSalarioNeto para incluir bonificaciones
     */
    @Override
    public double calcularSalarioNeto() {
        double sueldoConBonificaciones = calcularSueldoConBonificaciones();
        // Calculamos descuentos sobre el sueldo total (base + bonificaciones)
        double descuentoISSS = sueldoConBonificaciones * DESCUENTO_ISSS;
        double descuentoAFP = sueldoConBonificaciones * DESCUENTO_AFP;
        double descuentoRenta = calcularDescuentoRentaConBonificaciones(sueldoConBonificaciones);
        
        return sueldoConBonificaciones - (descuentoISSS + descuentoAFP + descuentoRenta);
    }
    
    /**
     * Calcula descuento de renta considerando bonificaciones
     */
    private double calcularDescuentoRentaConBonificaciones(double sueldoTotal) {
        if (sueldoTotal <= 472.00) {
            return 0.0;
        } else if (sueldoTotal <= 895.24) {
            return (sueldoTotal - 472.00) * 0.10;
        } else if (sueldoTotal <= 2038.10) {
            return 42.32 + ((sueldoTotal - 895.24) * 0.20);
        } else {
            return 270.90 + ((sueldoTotal - 2038.10) * 0.30);
        }
    }
    
    /**
     * Implementación del método abstracto de la clase padre
     */
    @Override
    public String getTipoEmpleado() {
        return "GERENTE";
    }
    
    /**
     * Obtiene información resumida del gerente
     */
    public String getResumenGerencial() {
        return String.format("Gerente %s - Depto: %s - Empleados a cargo: %d - Auto empresa: %s",
                getNombreCompleto(), departamento, numeroEmpleadosACargo, 
                tieneAutoEmpresa ? "Sí" : "No");
    }
    
    /**
     * Override del método toString para incluir información específica del gerente
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(String.format(
            "Departamento: %s\n" +
            "Empleados a cargo: %d\n" +
            "Auto de empresa: %s\n" +
            "Bonificación por gestión: $%.2f\n" +
            "Bonificación por auto: $%.2f\n" +
            "Sueldo total con bonificaciones: $%.2f\n",
            departamento,
            numeroEmpleadosACargo,
            tieneAutoEmpresa ? "Sí" : "No",
            calcularBonificacionGestion(),
            calcularBonificacionAuto(),
            calcularSueldoConBonificaciones()
        ));
        
        if (!equiposACargo.isEmpty()) {
            sb.append("Equipos a cargo: ");
            sb.append(String.join(", ", equiposACargo));
            sb.append("\n");
        }
        
        return sb.toString();
    }
}