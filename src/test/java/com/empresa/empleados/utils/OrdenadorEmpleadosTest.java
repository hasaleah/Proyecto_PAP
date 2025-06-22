package com.empresa.empleados.utils;

import com.empresa.empleados.model.Empleado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class OrdenadorEmpleadosTest {

    private List<Empleado> empleados;

    @BeforeEach
    void setUp() {
        // Crear empleados simulados
        empleados = Arrays.asList(
            new EmpleadoMock("Ana", "Gómez", 4000.0, "Gerente"),
            new EmpleadoMock("Juan", "Cruz", 2500.0, "JefeArea"),
            new EmpleadoMock("Ricardo", "Ramírez", 2000.0, "Supervisor"),
            new EmpleadoMock("Carlos", "Pineda", 1500.0, "Tecnico")
        );
    }

    @Test
    void testOrdenarPorPrimerApellidoAscendente() {
        List<Empleado> ordenados = OrdenadorEmpleados.ordenarPorPrimerApellido(empleados, true);
        assertEquals(4, ordenados.size(), "Debe devolver 4 empleados");
        assertEquals("Cruz", ordenados.get(0).getPrimerApellido(), "El primero debe ser Cruz");
        assertEquals("Gómez", ordenados.get(1).getPrimerApellido(), "El segundo debe ser Gómez");
        assertEquals("Pineda", ordenados.get(2).getPrimerApellido(), "El tercero debe ser Pineda");
        assertEquals("Ramírez", ordenados.get(3).getPrimerApellido(), "El cuarto debe ser Ramírez");
    }

    @Test
    void testOrdenarPorPrimerApellidoDescendente() {
        List<Empleado> ordenados = OrdenadorEmpleados.ordenarPorPrimerApellido(empleados, false);
        assertEquals(4, ordenados.size(), "Debe devolver 4 empleados");
        assertEquals("Ramírez", ordenados.get(0).getPrimerApellido(), "El primero debe ser Ramírez");
        assertEquals("Pineda", ordenados.get(1).getPrimerApellido(), "El segundo debe ser Pineda");
        assertEquals("Gómez", ordenados.get(2).getPrimerApellido(), "El tercero debe ser Gómez");
        assertEquals("Cruz", ordenados.get(3).getPrimerApellido(), "El cuarto debe ser Cruz");
    }

    @Test
    void testOrdenarPorPrimerApellidoListaVacia() {
        List<Empleado> ordenados = OrdenadorEmpleados.ordenarPorPrimerApellido(List.of(), true);
        assertTrue(ordenados.isEmpty(), "Debe devolver una lista vacía para entrada vacía");
    }

    @Test
    void testOrdenarPorPrimerApellidoListaNula() {
        List<Empleado> ordenados = OrdenadorEmpleados.ordenarPorPrimerApellido(null, true);
        assertTrue(ordenados.isEmpty(), "Debe devolver una lista vacía para entrada nula");
    }

    @Test
    void testOrdenarPorSueldoNetoAscendente() {
        List<Empleado> ordenados = OrdenadorEmpleados.ordenarPorSueldoNeto(empleados, true);
        assertEquals(4, ordenados.size(), "Debe devolver 4 empleados");
        assertEquals(1500.0, ordenados.get(0).calcularSalarioNeto(), 0.01, "El primero debe tener salario $1500");
        assertEquals(2000.0, ordenados.get(1).calcularSalarioNeto(), 0.01, "El segundo debe tener salario $2000");
        assertEquals(2500.0, ordenados.get(2).calcularSalarioNeto(), 0.01, "El tercero debe tener salario $2500");
        assertEquals(4000.0, ordenados.get(3).calcularSalarioNeto(), 0.01, "El cuarto debe tener salario $4000");
    }

    @Test
    void testOrdenarPorSueldoNetoDescendente() {
        List<Empleado> ordenados = OrdenadorEmpleados.ordenarPorSueldoNeto(empleados, false);
        assertEquals(4, ordenados.size(), "Debe devolver 4 empleados");
        assertEquals(4000.0, ordenados.get(0).calcularSalarioNeto(), 0.01, "El primero debe tener salario $4000");
        assertEquals(2500.0, ordenados.get(1).calcularSalarioNeto(), 0.01, "El segundo debe tener salario $2500");
        assertEquals(2000.0, ordenados.get(2).calcularSalarioNeto(), 0.01, "El tercero debe tener salario $2000");
        assertEquals(1500.0, ordenados.get(3).calcularSalarioNeto(), 0.01, "El cuarto debe tener salario $1500");
    }

    @Test
    void testOrdenarPorSueldoNetoListaVacia() {
        List<Empleado> ordenados = OrdenadorEmpleados.ordenarPorSueldoNeto(List.of(), true);
        assertTrue(ordenados.isEmpty(), "Debe devolver una lista vacía para entrada vacía");
    }

    @Test
    void testOrdenarPorSueldoNetoListaNula() {
        List<Empleado> ordenados = OrdenadorEmpleados.ordenarPorSueldoNeto(null, true);
        assertTrue(ordenados.isEmpty(), "Debe devolver una lista vacía para entrada nula");
    }

    @Test
    void testContarEmpleadosPorRol() {
        Map<String, Long> conteo = OrdenadorEmpleados.contarEmpleadosPorRol(empleados);
        assertEquals(4, conteo.size(), "Debe haber 4 tipos de empleados");
        assertEquals(1L, conteo.get("Gerente"), "Debe haber 1 Gerente");
        assertEquals(1L, conteo.get("JefeArea"), "Debe haber 1 JefeArea");
        assertEquals(1L, conteo.get("Supervisor"), "Debe haber 1 Supervisor");
        assertEquals(1L, conteo.get("Tecnico"), "Debe haber 1 Técnico");
    }

    @Test
    void testContarEmpleadosPorRolListaVacia() {
        Map<String, Long> conteo = OrdenadorEmpleados.contarEmpleadosPorRol(List.of());
        assertTrue(conteo.isEmpty(), "Debe devolver un mapa vacío para entrada vacía");
    }

    @Test
    void testContarEmpleadosPorRolListaNula() {
        Map<String, Long> conteo = OrdenadorEmpleados.contarEmpleadosPorRol(null);
        assertTrue(conteo.isEmpty(), "Debe devolver un mapa vacío para entrada nula");
    }

    // Clase auxiliar para simular Empleado
    private static class EmpleadoMock implements Empleado {
        private final String nombres;
        private final String primerApellido;
        private final double salarioNeto;
        private final String tipoEmpleado;

        public EmpleadoMock(String nombres, String primerApellido, double salarioNeto, String tipoEmpleado) {
            this.nombres = nombres;
            this.primerApellido = primerApellido;
            this.salarioNeto = salarioNeto;
            this.tipoEmpleado = tipoEmpleado;
        }

        @Override
        public String getPrimerApellido() {
            return primerApellido;
        }

        @Override
        public double calcularSalarioNeto() {
            return salarioNeto;
        }

        @Override
        public String getTipoEmpleado() {
            return tipoEmpleado;
        }

        // Métodos no utilizados en las pruebas
        @Override
        public String getNombreCompleto() { return nombres + " " + primerApellido; }
        @Override
        public String getSegundoApellido() { return ""; }
        @Override
        public String getDireccion() { return ""; }
        @Override
        public LocalDate getFechaNacimiento() { return LocalDate.now(); }
        @Override
        public char getSexo() { return 'M'; }
        @Override
        public String getTelefono() { return ""; }
        @Override
        public String getEmail() { return ""; }
        @Override
        public double calcularSueldoConBonificaciones() { return salarioNeto; }
        @Override
        public double calcularTotalDescuentos(double sueldoBruto) { return 0.0; }
        @Override
        public String getNombres() { return nombres; }
        @Override
        public void setNombres(String nombres) {}
        @Override
        public void setPrimerApellido(String primerApellido) {}
        @Override
        public void setSegundoApellido(String segundoApellido) {}
        @Override
        public void setDireccion(String direccion) {}
        @Override
        public void setFechaNacimiento(LocalDate fechaNacimiento) {}
        @Override
        public void setSexo(char sexo) {}
        @Override
        public void setTelefono(String telefono) {}
        @Override
        public void setEmail(String email) {}
    }
}