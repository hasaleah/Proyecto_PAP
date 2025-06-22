package com.empresa.empleados.utils;

import com.empresa.empleados.model.Empleado;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Utilidad para ordenar empleados y contarlos por rol.
 */
public class OrdenadorEmpleados {
    
	//Ordena la lista de empleados por primer apellido
    public static List<Empleado> ordenarPorPrimerApellido(List<Empleado> empleados, boolean ascending) {
        if (empleados == null || empleados.isEmpty()) {
            return List.of(); // Devuelve lista vacía si la entrada es nula o vacía
        }

        Comparator<Empleado> comparador = Comparator.comparing(Empleado::getPrimerApellido, String.CASE_INSENSITIVE_ORDER);
        if (!ascending) {
            comparador = comparador.reversed();
        }

        return empleados.stream()
                .sorted(comparador)
                .collect(Collectors.toList());
    }
	
	
	//Ordena la lista de empleados por sueldo neto (con descuentos).
    public static List<Empleado> ordenarPorSueldoNeto(List<Empleado> empleados, boolean ascendente) {
        if (empleados == null || empleados.isEmpty()) {
            return List.of(); // Devuelve lista vacía si la entrada es nula o vacía
        }

        Comparator<Empleado> comparador = Comparator.comparingDouble(Empleado::calcularSalarioNeto);
        if (!ascendente) {
            comparador = comparador.reversed();
        }

        return empleados.stream()
                .sorted(comparador)
                .collect(Collectors.toList());
    }

    /**
     * Cuenta la cantidad de empleados por tipo de rol.
     *
     * @param empleados Lista de empleados
     * @return Mapa con el tipo de empleado como clave y la cantidad como valor
     */
    public static Map<String, Long> contarEmpleadosPorRol(List<Empleado> empleados) {
        if (empleados == null || empleados.isEmpty()) {
            return Map.of(); // Devuelve mapa vacío si la entrada es nula o vacía
        }

        return empleados.stream()
                .collect(Collectors.groupingBy(
                        Empleado::getTipoEmpleado,
                        Collectors.counting()
                ));
    }
}