package com.empresa.empleados.service;

import com.empresa.empleados.model.Empleado;
import com.empresa.empleados.model.Gerente;
import com.empresa.empleados.model.JefeArea;
import com.empresa.empleados.model.Supervisor;
import com.empresa.empleados.model.Tecnico;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Servicio de alto nivel para gestionar la nómina de la empresa
 * Coordina operaciones con EmpleadoService y CalculadoraDescuentos
 */
public class NominaServices {
    
    private final EmpleadoService empleadoService;
    
    /**
     * Constructor que inicializa el servicio con una instancia de EmpleadoService
     * @param empleadoService Servicio para gestionar empleados
     */
    public NominaServices(EmpleadoService empleadoService) {
        if (empleadoService == null) {
            throw new IllegalArgumentException("El servicio de empleados no puede ser nulo");
        }
        this.empleadoService = empleadoService;
    }
    
    // GESTIÓN DE EMPLEADOS
    
    /**
     * Agrega un nuevo empleado a la nómina
     * @param empleado Empleado a agregar
     * @return true si se agregó correctamente
     */
    public boolean agregarEmpleado(Empleado empleado) {
        return empleadoService.agregarEmpleado(empleado);
    }
    
    /**
     * Agrega múltiples empleados a la nómina
     * @param empleados Lista de empleados a agregar
     */
    public void agregarEmpleados(List<Empleado> empleados) {
        empleadoService.agregarEmpleados(empleados);
    }
    
    /**
     * Obtiene todos los empleados registrados en la nómina
     * @return Lista de empleados
     */
    public List<Empleado> obtenerTodosLosEmpleados() {
        return empleadoService.obtenerTodosLosEmpleados();
    }
    
    /**
     * Obtiene el número total de empleados en la nómina
     * @return Total de empleados
     */
    public int obtenerTotalEmpleados() {
        return empleadoService.obtenerTotalEmpleados();
    }
    
    // =============== OPERACIONES DE NÓMINA ===============
    
    /**
     * Calcula el costo total de la nómina (suma de salarios netos)
     * @return Costo total de la nómina
     */
    public double calcularCostoTotalNomina() {
        return empleadoService.calcularTotalNomina();
    }
    
    /**
     * Calcula el total de descuentos legales aplicados a la nómina
     * @return Total de descuentos
     */
    public double calcularTotalDescuentosNomina() {
        return empleadoService.calcularTotalDescuentos();
    }
    
    /**
     * Obtiene un resumen detallado de descuentos para todos los empleados
     * @return Lista de resúmenes de descuentos
     */
    public List<CalculadoraDescuentos.ResumenDescuentos> obtenerResumenDescuentosNomina() {
        return obtenerTodosLosEmpleados().stream()
                .map(e -> CalculadoraDescuentos.obtenerResumenDescuentos(e.calcularSueldoConBonificaciones()))
                .collect(Collectors.toList());
    }
    
    // =============== REPORTES Y ANÁLISIS ===============
    
    /**
     * Genera un reporte de empleados ordenados por primer apellido
     * @return Lista de empleados ordenada
     */
    public List<Empleado> generarReportePorApellido() {
        return empleadoService.ordenarPorPrimerApellido();
    }
    
    /**
     * Genera un reporte de empleados ordenados por salario neto (ascendente)
     * @return Lista de empleados ordenada
     */
    public List<Empleado> generarReportePorSalarioAscendente() {
        return empleadoService.ordenarPorSalarioAscendente();
    }
    
    /**
     * Genera un reporte de empleados ordenados por salario neto (descendente)
     * @return Lista de empleados ordenada
     */
    public List<Empleado> generarReportePorSalarioDescendente() {
        return empleadoService.ordenarPorSalarioDescendente();
    }
    
    /**
     * Genera un conteo de empleados por tipo/rol
     * @return Mapa con el conteo por tipo de empleado
     */
    public Map<String, Long> generarConteoPorTipo() {
        return empleadoService.contarEmpleadosPorTipo();
    }
    
    /**
     * Obtiene estadísticas generales de la nómina
     * @return Estadísticas de salarios
     */
    public EmpleadoService.EstadisticasSalarios obtenerEstadisticasNomina() {
        return empleadoService.obtenerEstadisticasSalarios();
    }
    
    /**
     * Genera un reporte de empleados que cumplen años en un mes específico
     * @param mes Mes del año (1-12)
     * @return Lista de empleados que cumplen años en el mes
     */
    public List<Empleado> generarReporteCumpleañosPorMes(int mes) {
        if (mes < 1 || mes > 12) {
            return new ArrayList<>();
        }
        return empleadoService.obtenerCumpleañosPorMes(mes);
    }
    
    // =============== FILTROS ESPECÍFICOS ===============
    
    /**
     * Filtra empleados por tipo de empleado
     * @param tipoEmpleado Tipo de empleado (GERENTE, JEFE DE ÁREA, SUPERVISOR, TÉCNICO)
     * @return Lista de empleados filtrada
     */
    public List<Empleado> filtrarPorTipo(String tipoEmpleado) {
        return empleadoService.filtrarPorTipo(tipoEmpleado);
    }
    
    /**
     * Filtra empleados por rango de salario neto
     * @param salarioMinimo Salario mínimo del rango
     * @param salarioMaximo Salario máximo del rango
     * @return Lista de empleados filtrada
     */
    public List<Empleado> filtrarPorRangoSalario(double salarioMinimo, double salarioMaximo) {
        if (salarioMinimo < 0 || salarioMaximo < salarioMinimo) {
            return new ArrayList<>();
        }
        return empleadoService.filtrarPorRangoSalario(salarioMinimo, salarioMaximo);
    }
    
    /**
     * Filtra empleados por sexo
     * @param sexo Sexo del empleado ('M' o 'F')
     * @return Lista de empleados filtrada
     */
    public List<Empleado> filtrarPorSexo(char sexo) {
        if (sexo != 'M' && sexo != 'F') {
            return new ArrayList<>();
        }
        return empleadoService.filtrarPorSexo(sexo);
    }
    
    // =============== MÉTODOS ESPECÍFICOS POR TIPO ===============
    
    /**
     * Obtiene un reporte de gerentes con su resumen gerencial
     * @return Lista de resúmenes gerenciales
     */
    public List<String> generarReporteGerentes() {
        return empleadoService.obtenerGerentes().stream()
                .map(Gerente::getResumenGerencial)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtiene un reporte de jefes de área con su resumen
     * @return Lista de resúmenes de jefes de área
     */
    public List<String> generarReporteJefesArea() {
        return empleadoService.obtenerJefesArea().stream()
                .map(JefeArea::getResumenJefeArea)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtiene un reporte de supervisores con su resumen
     * @return Lista de resúmenes de supervisores
     */
    public List<String> generarReporteSupervisores() {
        return empleadoService.obtenerSupervisores().stream()
                .map(Supervisor::getResumenSupervisor)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtiene un reporte de técnicos con su resumen
     * @return Lista de resúmenes de técnicos
     */
    public List<String> generarReporteTecnicos() {
        return empleadoService.obtenerTecnicos().stream()
                .map(Tecnico::getResumenTecnico)
                .collect(Collectors.toList());
    }
    
    // =============== VALIDACIONES Y UTILIDADES ===============
    
    /**
     * Verifica si un empleado ya está registrado en la nómina
     * @param nombreCompleto Nombre completo del empleado
     * @return true si el empleado existe
     */
    public boolean existeEmpleado(String nombreCompleto) {
        return empleadoService.existeEmpleado(nombreCompleto);
    }
    
    /**
     * Obtiene un resumen de la nómina por tipo de empleado
     * @return Mapa con el costo total de nómina por tipo
     */
    public Map<String, Double> obtenerCostoNominaPorTipo() {
        return obtenerTodosLosEmpleados().stream()
                .collect(Collectors.groupingBy(
                        Empleado::getTipoEmpleado,
                        Collectors.summingDouble(Empleado::calcularSalarioNeto)
                ));
    }
}