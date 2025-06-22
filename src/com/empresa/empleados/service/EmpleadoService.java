package com.empresa.empleados.service;

import com.empresa.empleados.model.Empleado;
import com.empresa.empleados.model.Gerente;
import com.empresa.empleados.model.Tecnico;
import com.empresa.empleados.model.JefeArea;
import com.empresa.empleados.model.Supervisor;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar operaciones sobre empleados
 * Proporciona funcionalidades de búsqueda, filtrado y análisis
 */
public class EmpleadoService {
    
    private List<Empleado> empleados;
    
    /**
     * Constructor que inicializa la lista de empleados
     */
    public EmpleadoService() {
        this.empleados = new ArrayList<>();
    }
    
    /**
     * Constructor lista inicial de empleados
     */
    public EmpleadoService(List<Empleado> empleados) {
        this.empleados = new ArrayList<>(empleados);
    }
    
    // =============== OPERACIONES BÁSICAS ===============
    
    /**
     * Agrega un empleado a la lista
     */
    public boolean agregarEmpleado(Empleado empleado) {
        if (empleado == null) return false;
        return empleados.add(empleado);
    }
    
    /**
     * Agrega múltiples empleados a la lista
     */
    public void agregarEmpleados(List<Empleado> nuevosEmpleados) {
        if (nuevosEmpleados != null) {
            empleados.addAll(nuevosEmpleados);
        }
    }
    
    /**
     * Obtiene la lista completa de empleados
     */
    public List<Empleado> obtenerTodosLosEmpleados() {
        return new ArrayList<>(empleados);
    }
    
    /**
     * Obtiene el número total de empleados
     */
    public int obtenerTotalEmpleados() {
        return empleados.size();
    }
    
    /**
     * Limpia la lista de empleados
     */
    public void limpiarEmpleados() {
        empleados.clear();
    }
    
    // =============== BÚSQUEDAS Y FILTROS ===============
    
    /**
     * Busca empleados por nombre (coincidencia parcial, ignora mayúsculas)
     */
    public List<Empleado> buscarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        String nombreBusqueda = nombre.toLowerCase().trim();
        return empleados.stream()
            .filter(e -> e.getNombreCompleto().toLowerCase().contains(nombreBusqueda))
            .collect(Collectors.toList());
    }
    
    /**
     * Busca empleados por primer apellido (coincidencia exacta, ignora mayúsculas)
     */
    public List<Empleado> buscarPorPrimerApellido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        String apellidoBusqueda = apellido.toLowerCase().trim();
        return empleados.stream()
            .filter(e -> e.getPrimerApellido().toLowerCase().equals(apellidoBusqueda))
            .collect(Collectors.toList());
    }
    
    /**
     * Filtra empleados por tipo específico
     */
    public List<Empleado> filtrarPorTipo(String tipoEmpleado) {
        if (tipoEmpleado == null) return new ArrayList<>();
        
        return empleados.stream()
            .filter(e -> e.getTipoEmpleado().equalsIgnoreCase(tipoEmpleado))
            .collect(Collectors.toList());
    }
    
    /**
     * Filtra empleados por rango de salario neto
     */
    public List<Empleado> filtrarPorRangoSalario(double salarioMinimo, double salarioMaximo) {
        return empleados.stream()
            .filter(e -> {
                double salarioNeto = e.calcularSalarioNeto();
                return salarioNeto >= salarioMinimo && salarioNeto <= salarioMaximo;
            })
            .collect(Collectors.toList());
    }
    
    /**
     * Filtra empleados por sexo
     */
    public List<Empleado> filtrarPorSexo(char sexo) {
        return empleados.stream()
            .filter(e -> e.getSexo() == sexo)
            .collect(Collectors.toList());
    }
    
    // =============== ORDENAMIENTO ===============
    
    /**
     * Ordena empleados alfabéticamente por primer apellido
     */
    public List<Empleado> ordenarPorPrimerApellido() {
        List<Empleado> empleadosOrdenados = new ArrayList<>(empleados);
        empleadosOrdenados.sort(Comparator.comparing(Empleado::getPrimerApellido));
        return empleadosOrdenados;
    }
    
    /**
     * Ordena empleados por salario neto (menor a mayor)
     */
    public List<Empleado> ordenarPorSalarioAscendente() {
        List<Empleado> empleadosOrdenados = new ArrayList<>(empleados);
        empleadosOrdenados.sort(Comparator.comparingDouble(Empleado::calcularSalarioNeto));
        return empleadosOrdenados;
    }
    
    /**
     * Ordena empleados por salario neto (mayor a menor)
     */
    public List<Empleado> ordenarPorSalarioDescendente() {
        List<Empleado> empleadosOrdenados = new ArrayList<>(empleados);
        empleadosOrdenados.sort(Comparator.comparingDouble(Empleado::calcularSalarioNeto).reversed());
        return empleadosOrdenados;
    }
    
    /**
     * Ordena empleados por nombre completo
     */
    public List<Empleado> ordenarPorNombreCompleto() {
        List<Empleado> empleadosOrdenados = new ArrayList<>(empleados);
        empleadosOrdenados.sort(Comparator.comparing(Empleado::getNombreCompleto));
        return empleadosOrdenados;
    }
    
    // =============== ESTADÍSTICAS Y CONTEOS ===============
    
    /**
     * Cuenta empleados por tipo/rol
     */
    public Map<String, Long> contarEmpleadosPorTipo() {
        return empleados.stream()
            .collect(Collectors.groupingBy(
                Empleado::getTipoEmpleado,
                Collectors.counting()
            ));
    }
    
    /**
     * Obtiene estadísticas de salarios
     */
    public EstadisticasSalarios obtenerEstadisticasSalarios() {
        if (empleados.isEmpty()) {
            return new EstadisticasSalarios(0, 0, 0, 0, 0);
        }
        
        DoubleSummaryStatistics stats = empleados.stream()
            .mapToDouble(Empleado::calcularSalarioNeto)
            .summaryStatistics();
        
        return new EstadisticasSalarios(
            stats.getCount(),
            stats.getSum(),
            stats.getAverage(),
            stats.getMin(),
            stats.getMax()
        );
    }
    
    /**
     * Obtiene el total de la nómina (suma de todos los salarios netos)
     */
    public double calcularTotalNomina() {
        return empleados.stream()
            .mapToDouble(Empleado::calcularSalarioNeto)
            .sum();
    }
    
    /**
     * Obtiene el total de descuentos de toda la nómina
     */
    public double calcularTotalDescuentos() {
        return empleados.stream()
            .mapToDouble(e -> e.calcularTotalDescuentos(e.calcularSueldoConBonificaciones()))
            .sum();
    }
    
    // =============== MÉTODOS ESPECÍFICOS POR TIPO ===============
    
    /**
     * Obtiene solo los gerentes
     */
    public List<Gerente> obtenerGerentes() {
        return empleados.stream()
            .filter(e -> e instanceof Gerente)
            .map(e -> (Gerente) e)
            .collect(Collectors.toList());
    }
    
    /**
     * Obtiene solo los técnicos
     */
    public List<Tecnico> obtenerTecnicos() {
        return empleados.stream()
            .filter(e -> e instanceof Tecnico)
            .map(e -> (Tecnico) e)
            .collect(Collectors.toList());
    }
    
    /**
     * Obtiene solo los jefes de área
     */
    public List<JefeArea> obtenerJefesArea() {
        return empleados.stream()
            .filter(e -> e instanceof JefeArea)
            .map(e -> (JefeArea) e)
            .collect(Collectors.toList());
    }
    
    /**
     * Obtiene solo los supervisores
     */
    public List<Supervisor> obtenerSupervisores() {
        return empleados.stream()
            .filter(e -> e instanceof Supervisor)
            .map(e -> (Supervisor) e)
            .collect(Collectors.toList());
    }
    
    // =============== VALIDACIONES Y UTILIDADES ===============
    
    /**
     * Verifica si existe un empleado con el mismo nombre completo
     */
    public boolean existeEmpleado(String nombreCompleto) {
        return empleados.stream()
            .anyMatch(e -> e.getNombreCompleto().equalsIgnoreCase(nombreCompleto));
    }
    
    /**
     * Obtiene empleados que cumplen años en un mes específico
     */
    public List<Empleado> obtenerCumpleañosPorMes(int mes) {
        return empleados.stream()
            .filter(e -> e.getFechaNacimiento().getMonthValue() == mes)
            .collect(Collectors.toList());
    }
    
    /**
     * Clase interna para estadísticas de salarios
     */
    public static class EstadisticasSalarios {
        private final long totalEmpleados;
        private final double sumaSalarios;
        private final double promedioSalarios;
        private final double salarioMinimo;
        private final double salarioMaximo;
        
        public EstadisticasSalarios(long totalEmpleados, double sumaSalarios, 
                                  double promedioSalarios, double salarioMinimo, 
                                  double salarioMaximo) {
            this.totalEmpleados = totalEmpleados;
            this.sumaSalarios = sumaSalarios;
            this.promedioSalarios = promedioSalarios;
            this.salarioMinimo = salarioMinimo;
            this.salarioMaximo = salarioMaximo;
        }
        
        // Getters
        public long getTotalEmpleados() { return totalEmpleados; }
        public double getSumaSalarios() { return sumaSalarios; }
        public double getPromedioSalarios() { return promedioSalarios; }
        public double getSalarioMinimo() { return salarioMinimo; }
        public double getSalarioMaximo() { return salarioMaximo; }
        
        @Override
        public String toString() {
            return String.format(
                "=== ESTADÍSTICAS DE SALARIOS ===\n" +
                "Total de empleados: %d\n" +
                "Suma total de salarios: $%.2f\n" +
                "Promedio de salarios: $%.2f\n" +
                "Salario mínimo: $%.2f\n" +
                "Salario máximo: $%.2f",
                totalEmpleados, sumaSalarios, promedioSalarios, 
                salarioMinimo, salarioMaximo
            );
        }
    }
}