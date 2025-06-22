package com.empresa.empleados.main;

import com.empresa.empleados.data.DataLoader;
import com.empresa.empleados.data.EmpleadoRepository;
import com.empresa.empleados.model.Empleado;
import com.empresa.empleados.utils.OrdenadorEmpleados;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Clase principal de la aplicación de gestión de empleados.
 */
public class SistemaEmpleadosApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static EmpleadoRepository repository;

    public static void main(String[] args) {
        try {
            // Inicializar el repositorio y cargar datos iniciales
            repository = new EmpleadoRepository();
            DataLoader loader = new DataLoader(repository);
            int empleadosCargados = loader.cargarDatosIniciales();

            System.out.println("Sistema de Gestión de Empleados");
            System.out.println("Empleados cargados inicialmente: " + empleadosCargados);

            // Iniciar el menú interactivo
            mostrarMenu();
        } finally {
            scanner.close(); // Asegura que el Scanner se cierre
        }
    }

    
    private static void mostrarMenu() {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n=== Menú Principal ===");
            System.out.println("1. Listar todos los empleados");
            System.out.println("2. Ordenar empleados por sueldo neto (menor a mayor)");
            System.out.println("3. Ordenar empleados por sueldo neto (mayor a menor)");
            System.out.println("4. Mostrar cantidad de empleados por rol");
            System.out.println("5. Ordenar empleados por primer apellido (A a Z)");
            System.out.println("6. Ordenar empleados por primer apellido (Z a A)");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción (1-7): ");

            try {
                int opcion = Integer.parseInt(scanner.nextLine().trim());

                switch (opcion) {
                    case 1:
                        listarEmpleados();
                        break;
                    case 2:
                        ordenarPorSueldo(true);
                        break;
                    case 3:
                        ordenarPorSueldo(false);
                        break;
                    case 4:
                        contarPorRol();
                        break;
                    case 5:
                        ordenarPorApellido(true);
                        break;
                    case 6:
                        ordenarPorApellido(false);
                        break;
                    case 7:
                        salir = true;
                        System.out.println("¡Gracias por usar el sistema!");
                        break;
                    default:
                        System.out.println("Opción inválida. Por favor, seleccione un número entre 1 y 7.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
            }
        }
    }

    private static void listarEmpleados() {
        List<Empleado> empleados = repository.obtenerTodosLosEmpleados();
        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
            return;
        }

        System.out.println("\nLista de Empleados:");
        for (int i = 0; i < empleados.size(); i++) {
            Empleado e = empleados.get(i);
            System.out.printf("%d. %s (%s) - Sueldo Neto: $%.2f%n",
                    i + 1, e.getNombreCompleto(), e.getTipoEmpleado(), e.calcularSalarioNeto());
        }
    }

    private static void ordenarPorSueldo(boolean ascendente) {
        List<Empleado> empleados = repository.obtenerTodosLosEmpleados();
        if (empleados.isEmpty()) {
            System.out.println("No hay empleados para ordenar.");
            return;
        }

        List<Empleado> ordenados = OrdenadorEmpleados.ordenarPorSueldoNeto(empleados, ascendente);
        String orden = ascendente ? "menor a mayor" : "mayor a menor";
        System.out.println("\nEmpleados ordenados por sueldo neto (" + orden + "):");
        for (int i = 0; i < ordenados.size(); i++) {
            Empleado e = ordenados.get(i);
            System.out.printf("%d. %s (%s): $%.2f%n",
                    i + 1, e.getNombreCompleto(), e.getTipoEmpleado(), e.calcularSalarioNeto());
        }
    }

    private static void ordenarPorApellido(boolean ascendente) {
        List<Empleado> empleados = repository.obtenerTodosLosEmpleados();
        if (empleados.isEmpty()) {
            System.out.println("No hay empleados para ordenar.");
            return;
        }

        List<Empleado> ordenados = OrdenadorEmpleados.ordenarPorPrimerApellido(empleados, ascendente);
        String orden = ascendente ? "A a Z" : "Z a A";
        System.out.println("\nEmpleados ordenados por primer apellido (" + orden + "):");
        for (int i = 0; i < ordenados.size(); i++) {
            Empleado e = ordenados.get(i);
            System.out.printf("%d. %s (%s) - Apellido: %s%n",
                    i + 1, e.getNombreCompleto(), e.getTipoEmpleado(), e.getPrimerApellido());
        }
    }

    private static void contarPorRol() {
        List<Empleado> empleados = repository.obtenerTodosLosEmpleados();
        if (empleados.isEmpty()) {
            System.out.println("No hay empleados para contar.");
            return;
        }

        Map<String, Long> conteo = OrdenadorEmpleados.contarEmpleadosPorRol(empleados);
        System.out.println("\nCantidad de empleados por rol:");
        conteo.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> System.out.printf("%s: %d%n", entry.getKey(), entry.getValue()));
    }
}