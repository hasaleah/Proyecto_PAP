package com.empresa.empleados.service;

import com.empresa.empleados.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class EmpleadoServiceTest {
    private EmpleadoService servicio;
    private Gerente gerente;
    private Tecnico tecnico;
    private JefeArea jefeArea;
    private Supervisor supervisor;

    @BeforeEach
    void setUp() {
        // Crear empleados de prueba
        gerente = new Gerente(
            "Ana Sofía", "Gómez", "Martínez",
            "Calle Principal 123, San Salvador", LocalDate.of(1975, 3, 15), 'F',
            "1234-5678", "ana.gomez@empresa.com",
            "Dirección General", 2, true
        );
        gerente.setSalarioBase(5000.0); // Suposición

        tecnico = new Tecnico(
            "Juan Carlos", "Pineda", "Alvarado",
            "Cuscatancingo, San Salvador", LocalDate.of(1990, 1, 14), 'M',
            "123456789", "juan.pineda@empresa.com",
            "Mecánica", 5, "Mañana", true
        );
        tecnico.setSalarioBase(2000.0); // Suposición

        jefeArea = new JefeArea(
            "María Elena", "Cruz", "Sánchez",
            "Colonia Escalón, San Salvador", LocalDate.of(1980, 5, 10), 'F',
            "2345-6789", "maria.cruz@empresa.com",
            "Operaciones", "Operativa", 10, true
        );
        jefeArea.setSalarioBase(3000.0); // Suposición

        supervisor = new Supervisor(
            "Ricardo Andrés", "Ramírez", "García",
            "Mejicanos, San Salvador", LocalDate.of(1985, 7, 12), 'M',
            "5678-9012", "ricardo.ramirez@empresa.com",
            "Producción", "Directa", 5, true
        );
        supervisor.setSalarioBase(2500.0); // Suposición

        // Inicializar el servicio con empleados
        servicio = new EmpleadoService(Arrays.asList(gerente, tecnico, jefeArea, supervisor));
    }

    @Test
    void testAgregarEmpleadoCasoNormal() {
        Empleado nuevo = new Tecnico(
            "Gabriela", "Molina", "Mendéz",
            "Ilopango, San Salvador", LocalDate.of(1992, 3, 12), 'F',
            "234567890", "gabriela.molina@empresa.com",
            "Electrónica", 3, "Noche", false
        );
        assertTrue(servicio.agregarEmpleado(nuevo), "Debe agregar el empleado correctamente");
        assertEquals(5, servicio.obtenerTotalEmpleados(), "Debe haber 5 empleados");
    }

    @Test
    void testAgregarEmpleadoNulo() {
        assertFalse(servicio.agregarEmpleado(null), "No debe agregar un empleado nulo");
        assertEquals(4, servicio.obtenerTotalEmpleados(), "El total de empleados no debe cambiar");
    }

    @Test
    void testAgregarEmpleadosMultiples() {
        List<Empleado> nuevos = Arrays.asList(
            new Tecnico(
                "Luis", "Rivas", "Guzmán",
                "San Marcos, San Salvador", LocalDate.of(1989, 10, 27), 'M',
                "345678901", "luis.rivas@empresa.com",
                "Informática", 6, "Mixto", true
            ),
            new Supervisor(
                "Carmen", "Díaz", "Mendoza",
                "Soyapango, San Salvador", LocalDate.of(1983, 4, 25), 'F',
                "6789-0123", "carmen.diaz@empresa.com",
                "Logística", "Indirecta", 4, false
            )
        );
        servicio.agregarEmpleados(nuevos);
        assertEquals(6, servicio.obtenerTotalEmpleados(), "Debe haber 6 empleados tras agregar múltiples");
    }

    @Test
    void testObtenerTodosLosEmpleados() {
        List<Empleado> todos = servicio.obtenerTodosLosEmpleados();
        assertEquals(4, todos.size(), "Debe devolver 4 empleados");
        assertTrue(todos.contains(gerente), "Debe contener al gerente");
        assertTrue(todos.contains(tecnico), "Debe contener al técnico");
    }

    @Test
    void testObtenerTotalEmpleadosVacio() {
        servicio.limpiarEmpleados();
        assertEquals(0, servicio.obtenerTotalEmpleados(), "Debe devolver 0 para lista vacía");
    }

    @Test
    void testBuscarPorNombreCoincidenciaParcial() {
        List<Empleado> resultados = servicio.buscarPorNombre("ana");
        assertEquals(2, resultados.size(), "Debe encontrar 2 empleados (Ana Sofía y María Elena)");
        assertTrue(resultados.contains(gerente), "Debe contener al gerente");
        assertTrue(resultados.contains(jefeArea), "Debe contener al jefe de área");
    }

    @Test
    void testBuscarPorNombreSinResultados() {
        List<Empleado> resultados = servicio.buscarPorNombre("xyz");
        assertTrue(resultados.isEmpty(), "No debe encontrar empleados con nombre 'xyz'");
    }

    @Test
    void testBuscarPorPrimerApellidoExacto() {
        List<Empleado> resultados = servicio.buscarPorPrimerApellido("Cruz");
        assertEquals(1, resultados.size(), "Debe encontrar 1 empleado con apellido 'Cruz'");
        assertEquals("María Elena Cruz Sánchez", resultados.get(0).getNombreCompleto());
    }

    @Test
    void testFiltrarPorTipoGerente() {
        List<Empleado> gerentes = servicio.filtrarPorTipo("Gerente");
        assertEquals(1, gerentes.size(), "Debe encontrar 1 gerente");
        assertEquals("Ana Sofía Gómez Martínez", gerentes.get(0).getNombreCompleto());
    }

    @Test
    void testFiltrarPorRangoSalario() {
        // Suponiendo salarios netos aproximados: Gerente ~$4000, JefeArea ~$2500, Supervisor ~$2000, Técnico ~$1500
        List<Empleado> resultados = servicio.filtrarPorRangoSalario(2000.0, 3000.0);
        assertEquals(2, resultados.size(), "Debe encontrar 2 empleados en el rango $2000-$3000");
        assertTrue(resultados.contains(jefeArea), "Debe contener al jefe de área");
        assertTrue(resultados.contains(supervisor), "Debe contener al supervisor");
    }

    @Test
    void testFiltrarPorSexoFemenino() {
        List<Empleado> resultados = servicio.filtrarPorSexo('F');
        assertEquals(2, resultados.size(), "Debe encontrar 2 empleados femeninos");
        assertTrue(resultados.contains(gerente), "Debe contener al gerente");
        assertTrue(resultados.contains(jefeArea), "Debe contener al jefe de área");
    }

    @Test
    void testOrdenarPorPrimerApellido() {
        List<Empleado> ordenados = servicio.ordenarPorPrimerApellido();
        assertEquals("Cruz", ordenados.get(0).getPrimerApellido(), "El primer empleado debe ser Cruz");
        assertEquals("Gómez", ordenados.get(1).getPrimerApellido(), "El segundo empleado debe ser Gómez");
    }

    @Test
    void testOrdenarPorSalarioDescendente() {
        List<Empleado> ordenados = servicio.ordenarPorSalarioDescendente();
        assertEquals("Ana Sofía Gómez Martínez", ordenados.get(0).getNombreCompleto(),
                "El primero debe ser el gerente con mayor salario");
        assertEquals("Juan Carlos Pineda Alvarado", ordenados.get(3).getNombreCompleto(),
                "El último debe ser el técnico con menor salario");
    }

    @Test
    void testContarEmpleadosPorTipo() {
        Map<String, Long> conteo = servicio.contarEmpleadosPorTipo();
        assertEquals(1L, conteo.get("Gerente"), "Debe haber 1 gerente");
        assertEquals(1L, conteo.get("Tecnico"), "Debe haber 1 técnico");
        assertEquals(1L, conteo.get("JefeArea"), "Debe haber 1 jefe de área");
        assertEquals(1L, conteo.get("Supervisor"), "Debe haber 1 supervisor");
    }

    @Test
    void testObtenerEstadisticasSalarios() {
        // Suponiendo salarios netos aproximados
        EmpleadoService.EstadisticasSalarios estadisticas = servicio.obtenerEstadisticasSalarios();
        assertEquals(4, estadisticas.getTotalEmpleados(), "Debe haber 4 empleados");
        assertTrue(estadisticas.getSumaSalarios() > 0, "La suma de salarios debe ser positiva");
        assertTrue(estadisticas.getPromedioSalarios() > 0, "El promedio de salarios debe ser positivo");
    }

    @Test
    void testObtenerEstadisticasSalariosListaVacia() {
        servicio.limpiarEmpleados();
        EmpleadoService.EstadisticasSalarios estadisticas = servicio.obtenerEstadisticasSalarios();
        assertEquals(0, estadisticas.getTotalEmpleados(), "Debe ser 0 para lista vacía");
        assertEquals(0.0, estadisticas.getSumaSalarios(), "La suma debe ser 0");
    }

    @Test
    void testCalcularTotalNomina() {
        // Suponiendo salarios netos aproximados: $4000 + $2500 + $2000 + $1500 = $10000
        assertTrue(servicio.calcularTotalNomina() > 0, "El total de la nómina debe ser positivo");
    }

    @Test
    void testObtenerGerentes() {
        List<Gerente> gerentes = servicio.obtenerGerentes();
        assertEquals(1, gerentes.size(), "Debe devolver 1 gerente");
        assertEquals(gerente, gerentes.get(0), "Debe ser el gerente creado");
    }

    @Test
    void testObtenerCumpleañosPorMes() {
        List<Empleado> cumpleanios = servicio.obtenerCumpleañosPorMes(3); // Marzo
        assertEquals(1, cumpleanios.size(), "Debe encontrar 1 empleado que cumple en marzo");
        assertEquals("Ana Sofía Gómez Martínez", cumpleanios.get(0).getNombreCompleto());
    }

    @Test
    void testExisteEmpleado() {
        assertTrue(servicio.existeEmpleado("Ana Sofía Gómez Martínez"), "Debe existir el gerente");
        assertFalse(servicio.existeEmpleado("No Existe"), "No debe existir un empleado con ese nombre");
    }
}