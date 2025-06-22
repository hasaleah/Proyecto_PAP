package com.empresa.empleados.data;

import com.empresa.empleados.model.Empleado;
import com.empresa.empleados.model.Gerente;
import com.empresa.empleados.model.JefeArea;
import com.empresa.empleados.model.Supervisor;
import com.empresa.empleados.model.Tecnico;

import java.time.LocalDate;
import java.util.List;

/**
 * Fábrica para crear instancias de las subclases de Empleado.
 */
public class EmpleadoFactory {

   
    public enum TipoEmpleado {
        GERENTE,
        JEFE_AREA,
        SUPERVISOR,
        TECNICO
    }

    // Crea una instancia de Empleado según el tipo
    public static Empleado crearEmpleado(TipoEmpleado tipo, String nombres, String primerApellido,
                                         String segundoApellido, String direccion,
                                         LocalDate fechaNacimiento, char sexo, String telefono,
                                         String email, Object... paramsEspecificos) {
        switch (tipo) {
            case GERENTE:
                if (paramsEspecificos.length >= 3 &&
                    paramsEspecificos[0] instanceof String &&
                    paramsEspecificos[1] instanceof Integer &&
                    paramsEspecificos[2] instanceof Boolean) {
                    String areaResponsable = (String) paramsEspecificos[0];
                    int nivelJerarquico = (Integer) paramsEspecificos[1];
                    boolean tieneAutoEmpresa = (Boolean) paramsEspecificos[2];
                    Gerente gerente = new Gerente(nombres, primerApellido, segundoApellido, direccion,
                            fechaNacimiento, sexo, telefono, email, areaResponsable,
                            nivelJerarquico, tieneAutoEmpresa);
                    // Configurar equiposACargo si se proporcionan
                    if (paramsEspecificos.length > 3 && paramsEspecificos[3] instanceof List) {
                        @SuppressWarnings("unchecked")
                        List<String> equipos = (List<String>) paramsEspecificos[3];
                        equipos.forEach(gerente::agregarEquipoACargo);
                    }
                    return gerente;
                }
                throw new IllegalArgumentException("Parámetros inválidos para Gerente: área, nivel, auto");

            case JEFE_AREA:
                if (paramsEspecificos.length >= 4 &&
                    paramsEspecificos[0] instanceof String &&
                    paramsEspecificos[1] instanceof String &&
                    paramsEspecificos[2] instanceof Integer &&
                    paramsEspecificos[3] instanceof Boolean) {
                    String area = (String) paramsEspecificos[0];
                    String tipoArea = (String) paramsEspecificos[1];
                    int numeroSubordinados = (Integer) paramsEspecificos[2];
                    boolean manejaPresupuesto = (Boolean) paramsEspecificos[3];
                    JefeArea jefe = new JefeArea(nombres, primerApellido, segundoApellido, direccion,
                            fechaNacimiento, sexo, telefono, email, area, tipoArea,
                            numeroSubordinados, manejaPresupuesto);
                    // Configurar subAreas, presupuesto, metas si se proporcionan
                    if (paramsEspecificos.length > 4 && paramsEspecificos[4] instanceof List) {
                        @SuppressWarnings("unchecked")
                        List<String> subAreas = (List<String>) paramsEspecificos[4];
                        subAreas.forEach(jefe::agregarSubArea);
                    }
                    if (paramsEspecificos.length > 5 && paramsEspecificos[5] instanceof Double) {
                        jefe.setPresupuestoAnualACargo((Double) paramsEspecificos[5]);
                    }
                    if (paramsEspecificos.length > 6 && paramsEspecificos[6] instanceof Integer) {
                        jefe.setMetasAlcanzadas((Integer) paramsEspecificos[6]);
                    }
                    if (paramsEspecificos.length > 7 && paramsEspecificos[7] instanceof Integer) {
                        jefe.setTotalMetas((Integer) paramsEspecificos[7]);
                    }
                    return jefe;
                }
                throw new IllegalArgumentException("Parámetros inválidos para JefeArea: área, tipo, subordinados, presupuesto");

            case SUPERVISOR:
                if (paramsEspecificos.length >= 4 &&
                    paramsEspecificos[0] instanceof String &&
                    paramsEspecificos[1] instanceof String &&
                    paramsEspecificos[2] instanceof Integer &&
                    paramsEspecificos[3] instanceof Boolean) {
                    String departamento = (String) paramsEspecificos[0];
                    String tipoSupervision = (String) paramsEspecificos[1];
                    int numeroSubordinados = (Integer) paramsEspecificos[2];
                    boolean lideraEquipo = (Boolean) paramsEspecificos[3];
                    Supervisor supervisor = new Supervisor(nombres, primerApellido, segundoApellido,
                            direccion, fechaNacimiento, sexo, telefono, email, departamento,
                            tipoSupervision, numeroSubordinados, lideraEquipo);
                    // Configurar días trabajados e incidentes si se proporcionan
                    if (paramsEspecificos.length > 4 && paramsEspecificos[4] instanceof Integer) {
                        supervisor.setDiasTrabajadosMes((Integer) paramsEspecificos[4]);
                    }
                    if (paramsEspecificos.length > 5 && paramsEspecificos[5] instanceof Integer) {
                        supervisor.setIncidentesResueltos((Integer) paramsEspecificos[5]);
                    }
                    if (paramsEspecificos.length > 6 && paramsEspecificos[6] instanceof Integer) {
                        supervisor.setTotalIncidentes((Integer) paramsEspecificos[6]);
                    }
                    return supervisor;
                }
                throw new IllegalArgumentException("Parámetros inválidos para Supervisor: departamento, supervisión, subordinados, lideraEquipo");

            case TECNICO:
                if (paramsEspecificos.length >= 4 &&
                    paramsEspecificos[0] instanceof String &&
                    paramsEspecificos[1] instanceof Integer &&
                    paramsEspecificos[2] instanceof String &&
                    paramsEspecificos[3] instanceof Boolean) {
                    String especialidad = (String) paramsEspecificos[0];
                    int horasExtrasMes = (Integer) paramsEspecificos[1];
                    String turno = (String) paramsEspecificos[2];
                    boolean esLider = (Boolean) paramsEspecificos[3];
                    Tecnico tecnico = new Tecnico(nombres, primerApellido, segundoApellido,
                            direccion, fechaNacimiento, sexo, telefono, email, especialidad,
                            horasExtrasMes, turno, esLider);
                    // Configurar certificaciones si se proporcionan
                    if (paramsEspecificos.length > 4 && paramsEspecificos[4] instanceof List) {
                        @SuppressWarnings("unchecked")
                        List<String> certificaciones = (List<String>) paramsEspecificos[4];
                        certificaciones.forEach(tecnico::agregarCertificacion);
                    }
                    return tecnico;
                }
                throw new IllegalArgumentException("Parámetros inválidos para Técnico: especialidad, horasExtrasMes, turno, esLider");

            default:
                throw new IllegalArgumentException("Tipo de empleado no soportado: " + tipo);
        }
    }
}