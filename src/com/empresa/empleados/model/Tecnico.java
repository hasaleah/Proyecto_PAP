package com.empresa.empleados.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Tecnico extends Empleado {
    private static final double SUELDO_TECNICO = 800.00;
    private String especialidad;
    private int horasExtrasMes;
    private String turno; // "Mañana", "Tarde", "Noche", "Mixto"
    private boolean esLider;
    private List<String> certificaciones;

    public Tecnico(String nombres, String primerApellido, String segundoApellido,
                   String direccion, LocalDate fechaNacimiento, char sexo,
                   String telefono, String email, String especialidad,
                   int horasExtrasMes, String turno, boolean esLider) {
        super(nombres, primerApellido, segundoApellido, direccion,
              fechaNacimiento, sexo, telefono, email, SUELDO_TECNICO);
        this.especialidad = especialidad;
        this.horasExtrasMes = horasExtrasMes;
        this.turno = turno;
        this.esLider = esLider;
        this.certificaciones = new ArrayList<>();
    }

    // Alternative constructor
    public Tecnico(String nombres, String primerApellido, String segundoApellido,
                   String direccion, LocalDate fechaNacimiento, char sexo,
                   String telefono, String email, String especialidad) {
        this(nombres, primerApellido, segundoApellido, direccion, fechaNacimiento,
             sexo, telefono, email, especialidad, 0, "Mañana", false);
    }

    // Getters
    public String getEspecialidad() { return especialidad; }
    public int getHorasExtrasMes() { return horasExtrasMes; }
    public String getTurno() { return turno; }
    public boolean isEsLider() { return esLider; }
    public List<String> getCertificaciones() { return certificaciones; }

    // Setters
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    public void setHorasExtrasMes(int horasExtrasMes) { this.horasExtrasMes = horasExtrasMes; }
    public void setTurno(String turno) { this.turno = turno; }
    public void setEsLider(boolean esLider) { this.esLider = esLider; }

    public void agregarCertificacion(String certificacion) {
        if (!certificaciones.contains(certificacion)) {
            certificaciones.add(certificacion);
        }
    }

    public void removerCertificacion(String certificacion) {
        certificaciones.remove(certificacion);
    }

    // Bonifications
    public double calcularBonificacionCertificaciones() {
        return certificaciones.size() * sueldoBase * 0.02; // 2% per certification
    }

    public double calcularBonificacionHorasExtras() {
        return horasExtrasMes * 10.0; // $10 per overtime hour
    }

    public double calcularBonificacionLiderazgo() {
        return esLider ? sueldoBase * 0.03 : 0.0; // 3% if leader
    }

    @Override
    public double calcularBonificaciones() {
        return calcularBonificacionCertificaciones() + calcularBonificacionHorasExtras() + calcularBonificacionLiderazgo();
    }

    @Override
    public String getTipoEmpleado() {
        return "TÉCNICO";
    }

    public String getResumenTecnico() {
        return String.format("Técnico %s - Especialidad: %s - Turno: %s - Horas extra: %d",
                getNombreCompleto(), especialidad, turno, horasExtrasMes);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(String.format(
            "Especialidad: %s\n" +
            "Horas extra: %d\n" +
            "Turno: %s\n" +
            "Es líder: %s\n" +
            "Bonificación por certificaciones: $%.2f\n" +
            "Bonificación por horas extra: $%.2f\n" +
            "Bonificación por liderazgo: $%.2f\n",
            especialidad,
            horasExtrasMes,
            turno,
            esLider ? "Sí" : "No",
            calcularBonificacionCertificaciones(),
            calcularBonificacionHorasExtras(),
            calcularBonificacionLiderazgo()
        ));

        if (!certificaciones.isEmpty()) {
            sb.append("Certificaciones: ");
            sb.append(String.join(", ", certificaciones));
            sb.append("\n");
        }

        return sb.toString();
    }
}