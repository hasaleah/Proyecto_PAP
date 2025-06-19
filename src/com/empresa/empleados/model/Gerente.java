package com.empresa.empleados.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Salario fijo de $5,000
public class Gerente extends Empleado {
    // Atributos específicos del gerente
    private String departamento;
    private List<String> equiposACargo;
    private int numeroEmpleadosACargo;
    private boolean tieneAutoEmpresa;
    
    private static final double SUELDO_GERENTE = 5000.00;
    
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
    
    // Constructor alternativo
    public Gerente(String nombres, String primerApellido, String segundoApellido,
                   String direccion, LocalDate fechaNacimiento, char sexo,
                   String telefono, String email, String departamento) {
        this(nombres, primerApellido, segundoApellido, direccion, fechaNacimiento,
             sexo, telefono, email, departamento, 0, false);
    }
    
    // Getters
    public String getDepartamento() { return departamento; }
    public List<String> getEquiposACargo() { return equiposACargo; }
    public int getNumeroEmpleadosACargo() { return numeroEmpleadosACargo; }
    public boolean isTieneAutoEmpresa() { return tieneAutoEmpresa; }
    
    // Setters
    public void setDepartamento(String departamento) { this.departamento = departamento; }
    public void setNumeroEmpleadosACargo(int numeroEmpleadosACargo) { 
        this.numeroEmpleadosACargo = numeroEmpleadosACargo; 
    }
    public void setTieneAutoEmpresa(boolean tieneAutoEmpresa) { 
        this.tieneAutoEmpresa = tieneAutoEmpresa; 
    }
    
    // Agrega un equipo a la lista de equipos a cargo
    public void agregarEquipoACargo(String nombreEquipo) {
        if (!equiposACargo.contains(nombreEquipo)) {
            equiposACargo.add(nombreEquipo);
        }
    }
    
    // Elimina un equipo de la lista de equipos a cargo
    public void removerEquipoACargo(String nombreEquipo) {
        equiposACargo.remove(nombreEquipo);
    }
    
    // Calcula bonificación por gestión (5% del sueldo base por cada 10 empleados)
    public double calcularBonificacionGestion() {
        if (numeroEmpleadosACargo >= 10) {
            int gruposDeDiez = numeroEmpleadosACargo / 10;
            return sueldoBase * 0.05 * gruposDeDiez;
        }
        return 0.0;
    }
    
    // Calcula bonificación por auto de empresa
    public double calcularBonificacionAuto() {
        return tieneAutoEmpresa ? 200.00 : 0.0;
    }
    
    // Implementa el cálculo de bonificaciones totales
    @Override
    public double calcularBonificaciones() {
        return calcularBonificacionGestion() + calcularBonificacionAuto();
    }
    
    @Override
    public String getTipoEmpleado() {
        return "GERENTE";
    }
    
    public String getResumenGerencial() {
        return String.format("Gerente %s - Depto: %s - Empleados a cargo: %d - Auto empresa: %s",
                getNombreCompleto(), departamento, numeroEmpleadosACargo, 
                tieneAutoEmpresa ? "Sí" : "No");
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(String.format(
            "Departamento: %s\n" +
            "Empleados a cargo: %d\n" +
            "Auto de empresa: %s\n" +
            "Bonificación por gestión: $%.2f\n" +
            "Bonificación por auto: $%.2f\n",
            departamento,
            numeroEmpleadosACargo,
            tieneAutoEmpresa ? "Sí" : "No",
            calcularBonificacionGestion(),
            calcularBonificacionAuto()
        ));
        
        if (!equiposACargo.isEmpty()) {
            sb.append("Equipos a cargo: ");
            sb.append(String.join(", ", equiposACargo));
            sb.append("\n");
        }
        
        return sb.toString();
    }
}