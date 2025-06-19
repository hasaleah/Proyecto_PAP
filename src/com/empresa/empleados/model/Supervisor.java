package com.empresa.empleados.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Supervisor extends Empleado {
    private static final double SUELDO_SUPERVISOR = 1000.00;
    private String departamento;
    private String tipoSupervision; // "Directa", "Indirecta", "Mixta"
    private int numeroSubordinados;
    private boolean lideraEquipo;
    private int diasTrabajadosMes;
    private int incidentesResueltos;
    private int totalIncidentes;
    private List<String> equiposSupervisados;

    public Supervisor(String nombres, String primerApellido, String segundoApellido,
                      String direccion, LocalDate fechaNacimiento, char sexo,
                      String telefono, String email, String departamento,
                      String tipoSupervision, int numeroSubordinados, boolean lideraEquipo) {
        super(nombres, primerApellido, segundoApellido, direccion,
              fechaNacimiento, sexo, telefono, email, SUELDO_SUPERVISOR);
        this.departamento = departamento;
        this.tipoSupervision = tipoSupervision;
        this.numeroSubordinados = numeroSubordinados;
        this.lideraEquipo = lideraEquipo;
        this.diasTrabajadosMes = 0;
        this.incidentesResueltos = 0;
        this.totalIncidentes = 0;
        this.equiposSupervisados = new ArrayList<>();
    }

    // Alternative constructor
    public Supervisor(String nombres, String primerApellido, String segundoApellido,
                      String direccion, LocalDate fechaNacimiento, char sexo,
                      String telefono, String email, String departamento) {
        this(nombres, primerApellido, segundoApellido, direccion, fechaNacimiento,
             sexo, telefono, email, departamento, "Directa", 0, false);
    }

    // Getters
    public String getDepartamento() { return departamento; }
    public String getTipoSupervision() { return tipoSupervision; }
    public int getNumeroSubordinados() { return numeroSubordinados; }
    public boolean isLideraEquipo() { return lideraEquipo; }
    public int getDiasTrabajadosMes() { return diasTrabajadosMes; }
    public int getIncidentesResueltos() { return incidentesResueltos; }
    public int getTotalIncidentes() { return totalIncidentes; }
    public List<String> getEquiposSupervisados() { return equiposSupervisados; }

    // Setters
    public void setDepartamento(String departamento) { this.departamento = departamento; }
    public void setTipoSupervision(String tipoSupervision) { this.tipoSupervision = tipoSupervision; }
    public void setNumeroSubordinados(int numeroSubordinados) { this.numeroSubordinados = numeroSubordinados; }
    public void setLideraEquipo(boolean lideraEquipo) { this.lideraEquipo = lideraEquipo; }
    public void setDiasTrabajadosMes(int diasTrabajadosMes) { this.diasTrabajadosMes = diasTrabajadosMes; }
    public void setIncidentesResueltos(int incidentesResueltos) { this.incidentesResueltos = incidentesResueltos; }
    public void setTotalIncidentes(int totalIncidentes) { this.totalIncidentes = totalIncidentes; }

    public void agregarEquipoSupervisado(String nombreEquipo) {
        if (!equiposSupervisados.contains(nombreEquipo)) {
            equiposSupervisados.add(nombreEquipo);
        }
    }

    public void removerEquipoSupervisado(String nombreEquipo) {
        equiposSupervisados.remove(nombreEquipo);
    }

    // Calculate incident resolution percentage
    public double calcularPorcentajeIncidentesResueltos() {
        if (totalIncidentes == 0) return 0.0;
        return ((double) incidentesResueltos / totalIncidentes) * 100;
    }

    // Bonifications
    public double calcularBonificacionIncidentes() {
        double porcentaje = calcularPorcentajeIncidentesResueltos();
        if (porcentaje >= 90) return sueldoBase * 0.05; // 5% for ≥90% resolution
        if (porcentaje >= 75) return sueldoBase * 0.03; // 3% for ≥75% resolution
        return 0.0;
    }

    public double calcularBonificacionAsistencia() {
        return diasTrabajadosMes >= 20 ? sueldoBase * 0.02 : 0.0; // 2% for ≥20 days
    }

    public double calcularBonificacionLiderazgo() {
        return lideraEquipo ? sueldoBase * 0.03 : 0.0; // 3% if leads team
    }

    @Override
    public double calcularBonificaciones() {
        return calcularBonificacionIncidentes() + calcularBonificacionAsistencia() + calcularBonificacionLiderazgo();
    }

    @Override
    public String getTipoEmpleado() {
        return "SUPERVISOR";
    }

    public String getResumenSupervisor() {
        return String.format("Supervisor %s - Depto: %s - Tipo: %s - Subordinados: %d - Incidentes Resueltos: %.1f%%",
                getNombreCompleto(), departamento, tipoSupervision, numeroSubordinados,
                calcularPorcentajeIncidentesResueltos());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(String.format(
            "Departamento: %s\n" +
            "Tipo de supervisión: %s\n" +
            "Número de subordinados: %d\n" +
            "Lidera equipo: %s\n" +
            "Días trabajados en el mes: %d\n" +
            "Incidentes resueltos: %d de %d (%.1f%%)\n" +
            "Bonificación por incidentes: $%.2f\n" +
            "Bonificación por asistencia: $%.2f\n" +
            "Bonificación por liderazgo: $%.2f\n",
            departamento,
            tipoSupervision,
            numeroSubordinados,
            lideraEquipo ? "Sí" : "No",
            diasTrabajadosMes,
            incidentesResueltos, totalIncidentes, calcularPorcentajeIncidentesResueltos(),
            calcularBonificacionIncidentes(),
            calcularBonificacionAsistencia(),
            calcularBonificacionLiderazgo()
        ));

        if (!equiposSupervisados.isEmpty()) {
            sb.append("Equipos supervisados: ");
            sb.append(String.join(", ", equiposSupervisados));
            sb.append("\n");
        }

        return sb.toString();
    }
}