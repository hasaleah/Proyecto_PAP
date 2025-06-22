package com.empresa.empleados.data;

import com.empresa.empleados.model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EmpleadoFactoryTest {

    private static final String NOMBRES = "Juan Carlos";
    private static final String PRIMER_APELLIDO = "Pérez";
    private static final String SEGUNDO_APELLIDO = "López";
    private static final String DIRECCION = "Calle Principal 123, San Salvador";
    private static final LocalDate FECHA_NACIMIENTO = LocalDate.of(1990, 5, 15);
    private static final char SEXO = 'M';
    private static final String TELEFONO = "1234-5678";
    private static final String EMAIL = "juan.perez@empresa.com";

    @Test
    void testCrearGerenteCasoNormal() {
        List<String> equipos = Arrays.asList("Ventas", "Marketing");
        Empleado empleado = EmpleadoFactory.crearEmpleado(
            EmpleadoFactory.TipoEmpleado.GERENTE,
            NOMBRES, PRIMER_APELLIDO, SEGUNDO_APELLIDO, DIRECCION,
            FECHA_NACIMIENTO, SEXO, TELEFONO, EMAIL,
            "Dirección General", 2, true, equipos
        );

        assertTrue(empleado instanceof Gerente, "Debe ser una instancia de Gerente");
        Gerente gerente = (Gerente) empleado;
        assertEquals("Juan Carlos Pérez López", gerente.getNombreCompleto(), "El nombre completo debe ser correcto");
        assertEquals("Dirección General", gerente.getAreaResponsable(), "El área debe ser Dirección General");
        assertEquals(2, gerente.getNivelJerarquico(), "El nivel jerárquico debe ser 2");
        assertTrue(gerente.getTieneAutoEmpresa(), "Debe tener auto de empresa");
        assertEquals(equipos, gerente.getEquiposACargo(), "Debe tener los equipos especificados");
    }

    @Test
    void testCrearGerenteParametrosInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> EmpleadoFactory.crearEmpleado(
            EmpleadoFactory.TipoEmpleado.GERENTE,
            NOMBRES, PRIMER_APELLIDO, SEGUNDO_APELLIDO, DIRECCION,
            FECHA_NACIMIENTO, SEXO, TELEFONO, EMAIL,
            "Dirección General", "2", true // Nivel jerárquico no es Integer
        ), "Debe lanzar excepción por parámetros inválidos para Gerente");
    }

    @Test
    void testCrearJefeAreaCasoNormal() {
        List<String> subAreas = Arrays.asList("Producción", "Logística");
        Empleado empleado = EmpleadoFactory.crearEmpleado(
            EmpleadoFactory.TipoEmpleado.JEFE_AREA,
            NOMBRES, PRIMER_APELLIDO, SEGUNDO_APELLIDO, DIRECCION,
            FECHA_NACIMIENTO, SEXO, TELEFONO, EMAIL,
            "Operaciones", "Operativa", 10, true, subAreas, 500000.0, 8, 10
        );

        assertTrue(empleado instanceof JefeArea, "Debe ser una instancia de JefeArea");
        JefeArea jefe = (JefeArea) empleado;
        assertEquals("Juan Carlos Pérez López", jefe.getNombreCompleto(), "El nombre completo debe ser correcto");
        assertEquals("Operaciones", jefe.getArea(), "El área debe ser Operaciones");
        assertEquals("Operativa", jefe.getTipoArea(), "El tipo de área debe ser Operativa");
        assertEquals(10, jefe.getNumeroSubordinados(), "Debe tener 10 subordinados");
        assertTrue(jefe.getManejaPresupuesto(), "Debe manejar presupuesto");
        assertEquals(subAreas, jefe.getSubAreas(), "Debe tener las subáreas especificadas");
        assertEquals(500000.0, jefe.getPresupuestoAnualACargo(), 0.01, "El presupuesto debe ser $500000");
        assertEquals(8, jefe.getMetasAlcanzadas(), "Debe tener 8 metas alcanzadas");
        assertEquals(10, jefe.getTotalMetas(), "Debe tener 10 metas totales");
    }

    @Test
    void testCrearJefeAreaSinOpcionales() {
        Empleado empleado = EmpleadoFactory.crearEmpleado(
            EmpleadoFactory.TipoEmpleado.JEFE_AREA,
            NOMBRES, PRIMER_APELLIDO, SEGUNDO_APELLIDO, DIRECCION,
            FECHA_NACIMIENTO, SEXO, TELEFONO, EMAIL,
            "Operaciones", "Operativa", 10, true
        );

        assertTrue(empleado instanceof JefeArea, "Debe ser una instancia de JefeArea");
        JefeArea jefe = (JefeArea) empleado;
        assertEquals("Operaciones", jefe.getArea(), "El área debe ser Operaciones");
        assertTrue(jefe.getSubAreas().isEmpty(), "No debe tener subáreas");
        assertEquals(0.0, jefe.getPresupuestoAnualACargo(), 0.01, "El presupuesto debe ser 0");
    }

    @Test
    void testCrearJefeAreaParametrosInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> EmpleadoFactory.crearEmpleado(
            EmpleadoFactory.TipoEmpleado.JEFE_AREA,
            NOMBRES, PRIMER_APELLIDO, SEGUNDO_APELLIDO, DIRECCION,
            FECHA_NACIMIENTO, SEXO, TELEFONO, EMAIL,
            "Operaciones", "Operativa", "10", true // Subordinados no es Integer
        ), "Debe lanzar excepción por parámetros inválidos para JefeArea");
    }

    @Test
    void testCrearSupervisorCasoNormal() {
        Empleado empleado = EmpleadoFactory.crearEmpleado(
            EmpleadoFactory.TipoEmpleado.SUPERVISOR,
            NOMBRES, PRIMER_APELLIDO, SEGUNDO_APELLIDO, DIRECCION,
            FECHA_NACIMIENTO, SEXO, TELEFONO, EMAIL,
            "Producción", "Directa", 5, true, 20, 18, 20
        );

        assertTrue(empleado instanceof Supervisor, "Debe ser una instancia de Supervisor");
        Supervisor supervisor = (Supervisor) empleado;
        assertEquals("Juan Carlos Pérez López", supervisor.getNombreCompleto(), "El nombre completo debe ser correcto");
        assertEquals("Producción", supervisor.getDepartamento(), "El departamento debe ser Producción");
        assertEquals("Directa", supervisor.getTipoSupervision(), "El tipo de supervisión debe ser Directa");
        assertEquals(5, supervisor.getNumeroSubordinados(), "Debe tener 5 subordinados");
        assertTrue(supervisor.getLideraEquipo(), "Debe liderar equipo");
        assertEquals(20, supervisor.getDiasTrabajadosMes(), "Debe tener 20 días trabajados");
        assertEquals(18, supervisor.getIncidentesResueltos(), "Debe tener 18 incidentes resueltos");
        assertEquals(20, supervisor.getTotalIncidentes(), "Debe tener 20 incidentes totales");
    }

    @Test
    void testCrearSupervisorParametrosInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> EmpleadoFactory.crearEmpleado(
            EmpleadoFactory.TipoEmpleado.SUPERVISOR,
            NOMBRES, PRIMER_APELLIDO, SEGUNDO_APELLIDO, DIRECCION,
            FECHA_NACIMIENTO, SEXO, TELEFONO, EMAIL,
            "Producción", "Directa", "5", true // Subordinados no es Integer
        ), "Debe lanzar excepción por parámetros inválidos para Supervisor");
    }

    @Test
    void testCrearTecnicoCasoNormal() {
        List<String> certificaciones = Arrays.asList("Certificación ISO 9001", "Certificación Cisco");
        Empleado empleado = EmpleadoFactory.crearEmpleado(
            EmpleadoFactory.TipoEmpleado.TECNICO,
            NOMBRES, PRIMER_APELLIDO, SEGUNDO_APELLIDO, DIRECCION,
            FECHA_NACIMIENTO, SEXO, TELEFONO, EMAIL,
            "Mecánica", 10, "Mañana", true, certificaciones
        );

        assertTrue(empleado instanceof Tecnico, "Debe ser una instancia de Técnico");
        Tecnico tecnico = (Tecnico) empleado;
        assertEquals("Juan Carlos Pérez López", tecnico.getNombreCompleto(), "El nombre completo debe ser correcto");
        assertEquals("Mecánica", tecnico.getEspecialidad(), "La especialidad debe ser Mecánica");
        assertEquals(10, tecnico.getHorasExtrasMes(), "Debe tener 10 horas extras");
        assertEquals("Mañana", tecnico.getTurno(), "El turno debe ser Mañana");
        assertTrue(tecnico.getEsLider(), "Debe ser líder");
        assertEquals(certificaciones, tecnico.getCertificaciones(), "Debe tener las certificaciones especificadas");
    }

    @Test
    void testCrearTecnicoSinCertificaciones() {
        Empleado empleado = EmpleadoFactory.crearEmpleado(
            EmpleadoFactory.TipoEmpleado.TECNICO,
            NOMBRES, PRIMER_APELLIDO, SEGUNDO_APELLIDO, DIRECCION,
            FECHA_NACIMIENTO, SEXO, TELEFONO, EMAIL,
            "Mecánica", 10, "Mañana", true
        );

        assertTrue(empleado instanceof Tecnico, "Debe ser una instancia de Técnico");
        Tecnico tecnico = (Tecnico) empleado;
        assertTrue(tecnico.getCertificaciones().isEmpty(), "No debe tener certificaciones");
    }

    @Test
    void testCrearTecnicoParametrosInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> EmpleadoFactory.crearEmpleado(
            EmpleadoFactory.TipoEmpleado.TECNICO,
            NOMBRES, PRIMER_APELLIDO, SEGUNDO_APELLIDO, DIRECCION,
            FECHA_NACIMIENTO, SEXO, TELEFONO, EMAIL,
            "Mecánica", "10", "Mañana", true // Horas extras no es Integer
        ), "Debe lanzar excepción por parámetros inválidos para Técnico");
    }

    @Test
    void testTipoEmpleadoNoSoportado() {
        assertThrows(IllegalArgumentException.class, () -> EmpleadoFactory.crearEmpleado(
            null, // Tipo nulo
            NOMBRES, PRIMER_APELLIDO, SEGUNDO_APELLIDO, DIRECCION,
            FECHA_NACIMIENTO, SEXO, TELEFONO, EMAIL
        ), "Debe lanzar excepción por tipo de empleado no soportado");
    }
}