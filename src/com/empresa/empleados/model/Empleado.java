package com.empresa.empleados.model;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

// Clase base de Empleado
public abstract class Empleado {
    // Atributos protegidos para que las clases hijas puedan acceder
    protected String nombres;
    protected String primerApellido;
    protected String segundoApellido;
    protected String direccion;
    protected LocalDate fechaNacimiento;
    protected char sexo; // 'M' para masculino, 'F' para femenino
    protected String telefono;
    protected String email;
    protected double sueldoBase;
    
    // Constantes para los descuentos
    protected static final double DESCUENTO_ISSS = 0.075;  
    protected static final double DESCUENTO_AFP = 0.0775;  
    
    // Constructor de la clase
    public Empleado(String nombres, String primerApellido, String segundoApellido, 
                   String direccion, LocalDate fechaNacimiento, char sexo, 
                   String telefono, String email, double sueldoBase) {
        this.nombres = nombres;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.telefono = telefono;
        this.email = email;
        this.sueldoBase = sueldoBase;
    }
    
    // Métodos getters
    public String getNombres() { return nombres; }
    public String getPrimerApellido() { return primerApellido; }
    public String getSegundoApellido() { return segundoApellido; }
    public String getDireccion() { return direccion; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public char getSexo() { return sexo; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }
    public double getSueldoBase() { return sueldoBase; }
    
    // Métodos setters
    public void setNombres(String nombres) { this.nombres = nombres; }
    public void setPrimerApellido(String primerApellido) { this.primerApellido = primerApellido; }
    public void setSegundoApellido(String segundoApellido) { this.segundoApellido = segundoApellido; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public void setSexo(char sexo) { this.sexo = sexo; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setEmail(String email) { this.email = email; }
    public void setSueldoBase(double sueldoBase) { this.sueldoBase = sueldoBase; }
    
    // Para calcular la edad del empleado
    public int calcularEdad() {
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }
    
    // Get el nombre completo del empleado
    public String getNombreCompleto() {
        return nombres + " " + primerApellido + " " + segundoApellido;
    }
    
    // Calcula el descuento de ISSS
    public double calcularDescuentoISSS(double sueldoTotal) {
        return sueldoTotal * DESCUENTO_ISSS;
    }
    
    // Calcula el descuento de AFP
    public double calcularDescuentoAFP(double sueldoTotal) {
        return sueldoTotal * DESCUENTO_AFP;
    }
    
    /**
     * Calcula el descuento de renta
     * Hasta $472.00 - 0%
     * De $472.01 a $895.24 - 10% sobre el exceso de $472.00
     * De $895.25 a $2,038.10 - $42.32 + 20% sobre el exceso de $895.24
     * Más de $2,038.10 - $270.90 + 30% sobre el exceso de $2,038.10
     */
    public double calcularDescuentoRenta(double sueldoTotal) {
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
    
    // Calcula el total de descuentos
    public double calcularTotalDescuentos(double sueldoTotal) {
        return calcularDescuentoISSS(sueldoTotal) + calcularDescuentoAFP(sueldoTotal) + calcularDescuentoRenta(sueldoTotal);
    }
    
    // Método abstracto para calcular bonificaciones, implementado por las subclases
    public abstract double calcularBonificaciones();
    
    // Calcula el sueldo total incluyendo bonificaciones
    public double calcularSueldoConBonificaciones() {
        return sueldoBase + calcularBonificaciones();
    }
    
    // Calcula el salario neto (después de descuentos, considerando bonificaciones)
    public double calcularSalarioNeto() {
        double sueldoConBonificaciones = calcularSueldoConBonificaciones();
        return sueldoConBonificaciones - calcularTotalDescuentos(sueldoConBonificaciones);
    }
    
    /**
     * Método que debe ser implementado por las clases hijas para identificar el tipo de empleado según la consulta
     */
    public abstract String getTipoEmpleado();
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        double sueldoConBonificaciones = calcularSueldoConBonificaciones();
        return String.format(
            "=== %s ===\n" +
            "Nombre: %s\n" +
            "Dirección: %s\n" +
            "Fecha Nacimiento: %s (Edad: %d años)\n" +
            "Sexo: %s\n" +
            "Teléfono: %s\n" +
            "Email: %s\n" +
            "Sueldo Base: $%.2f\n" +
            "Bonificaciones: $%.2f\n" +
            "Sueldo Total: $%.2f\n" +
            "Descuento ISSS (7.5%%): $%.2f\n" +
            "Descuento AFP (7.75%%): $%.2f\n" +
            "Descuento Renta: $%.2f\n" +
            "Total Descuentos: $%.2f\n" +
            "Salario Neto: $%.2f\n",
            getTipoEmpleado(),
            getNombreCompleto(),
            direccion,
            fechaNacimiento.format(formatter),
            calcularEdad(),
            (sexo == 'M') ? "Masculino" : "Femenino",
            telefono,
            email,
            sueldoBase,
            calcularBonificaciones(),
            sueldoConBonificaciones,
            calcularDescuentoISSS(sueldoConBonificaciones),
            calcularDescuentoAFP(sueldoConBonificaciones),
            calcularDescuentoRenta(sueldoConBonificaciones),
            calcularTotalDescuentos(sueldoConBonificaciones),
            calcularSalarioNeto()
        );
    }
}