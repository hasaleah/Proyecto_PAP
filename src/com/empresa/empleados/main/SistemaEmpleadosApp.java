package com.empresa.empleados.main;

import com.empresa.empleados.data.DataLoader;
import com.empresa.empleados.data.EmpleadoFactory;
import com.empresa.empleados.data.EmpleadoRepository;
import com.empresa.empleados.model.Empleado;
import com.empresa.empleados.utils.OrdenadorEmpleados;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Clase principal de la aplicación de gestión de empleados
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
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
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
            System.out.println("7. Agregar nuevo empleado");
            System.out.println("8. Salir");
            System.out.print("Seleccione una opción (1-8): ");

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
                        agregarEmpleado();
                        break;
                    case 8:
                        salir = true;
                        System.out.println("¡Gracias por usar el sistema!");
                        break;
                    default:
                        System.out.println("Opción inválida. Por favor, seleccione un número entre 1 y 8.");
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

    private static void agregarEmpleado() {
        System.out.println("\n=== Agregar Nuevo Empleado ===");
        try {
            // Seleccionar tipo de empleado
            System.out.println("Seleccione el tipo de empleado:");
            System.out.println("1. Gerente");
            System.out.println("2. Jefe de Área");
            System.out.println("3. Supervisor");
            System.out.println("4. Técnico");
            System.out.print("Opción (1-4): ");
            int tipoOpcion = Integer.parseInt(scanner.nextLine().trim());
            EmpleadoFactory.TipoEmpleado tipo;
            switch (tipoOpcion) {
                case 1: tipo = EmpleadoFactory.TipoEmpleado.GERENTE; break;
                case 2: tipo = EmpleadoFactory.TipoEmpleado.JEFE_AREA; break;
                case 3: tipo = EmpleadoFactory.TipoEmpleado.SUPERVISOR; break;
                case 4: tipo = EmpleadoFactory.TipoEmpleado.TECNICO; break;
                default: throw new IllegalArgumentException("Opción inválida. Seleccione un número entre 1 y 4.");
            }

            // Datos comunes
            System.out.print("Nombres: ");
            String nombres = scanner.nextLine().trim();
            System.out.print("Primer Apellido: ");
            String primerApellido = scanner.nextLine().trim();
            System.out.print("Segundo Apellido: ");
            String segundoApellido = scanner.nextLine().trim();
            System.out.print("Dirección: ");
            String direccion = scanner.nextLine().trim();
            System.out.print("Fecha de Nacimiento (YYYY-MM-DD): ");
            LocalDate fechaNacimiento = LocalDate.parse(scanner.nextLine().trim());
            System.out.print("Sexo (M/F): ");
            char sexo = scanner.nextLine().trim().toUpperCase().charAt(0);
            if (sexo != 'M' && sexo != 'F') throw new IllegalArgumentException("Sexo debe ser M o F.");
            System.out.print("Teléfono: ");
            String telefono = scanner.nextLine().trim();
            System.out.print("Email: ");
            String email = scanner.nextLine().trim();
            System.out.print("Salario Base ($): ");
            double salarioBase = Double.parseDouble(scanner.nextLine().trim());
            if (salarioBase <= 0) throw new IllegalArgumentException("El salario base debe ser positivo.");

            // Crear empleado según el tipo
            Empleado empleado;
            switch (tipo) {
                case GERENTE:
                    System.out.print("Área Responsable: ");
                    String areaResponsable = scanner.nextLine().trim();
                    System.out.print("Nivel Jerárquico (1-5): ");
                    int nivelJerarquico = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("¿Tiene auto de empresa? (S/N): ");
                    boolean tieneAutoEmpresa = scanner.nextLine().trim().toUpperCase().startsWith("S");
                    System.out.print("Equipos a cargo (separados por coma, o vacío): ");
                    String equiposInput = scanner.nextLine().trim();
                    List<String> equipos = equiposInput.isEmpty() ? new ArrayList<>() : List.of(equiposInput.split(","));
                    empleado = EmpleadoFactory.crearEmpleado(tipo, nombres, primerApellido, segundoApellido, direccion,
                            fechaNacimiento, sexo, telefono, email, areaResponsable, nivelJerarquico, tieneAutoEmpresa, equipos);
                    break;

                case JEFE_AREA:
                    System.out.print("Área: ");
                    String area = scanner.nextLine().trim();
                    System.out.print("Tipo de Área (Operativa/Administrativa): ");
                    String tipoArea = scanner.nextLine().trim();
                    System.out.print("Número de Subordinados: ");
                    int numeroSubordinados = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("¿Maneja Presupuesto? (S/N): ");
                    boolean manejaPresupuesto = scanner.nextLine().trim().toUpperCase().startsWith("S");
                    System.out.print("Subáreas (separadas por coma, o vacío): ");
                    String subAreasInput = scanner.nextLine().trim();
                    List<String> subAreas = subAreasInput.isEmpty() ? new ArrayList<>() : List.of(subAreasInput.split(","));
                    System.out.print("Presupuesto Anual ($): ");
                    double presupuesto = Double.parseDouble(scanner.nextLine().trim());
                    System.out.print("Metas Alcanzadas: ");
                    int metasAlcanzadas = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Total Metas: ");
                    int totalMetas = Integer.parseInt(scanner.nextLine().trim());
                    empleado = EmpleadoFactory.crearEmpleado(tipo, nombres, primerApellido, segundoApellido, direccion,
                            fechaNacimiento, sexo, telefono, email, area, tipoArea, numeroSubordinados, manejaPresupuesto,
                            subAreas, presupuesto, metasAlcanzadas, totalMetas);
                    break;

                case SUPERVISOR:
                    System.out.print("Departamento: ");
                    String departamento = scanner.nextLine().trim();
                    System.out.print("Tipo de Supervisión (Directa/Indirecta): ");
                    String tipoSupervision = scanner.nextLine().trim();
                    System.out.print("Número de Subordinados: ");
                    int numSubordinados = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("¿Lidera Equipo? (S/N): ");
                    boolean lideraEquipo = scanner.nextLine().trim().toUpperCase().startsWith("S");
                    System.out.print("Días Trabajados en el Mes: ");
                    int diasTrabajados = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Incidentes Resueltos: ");
                    int incidentesResueltos = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Total Incidentes: ");
                    int totalIncidentes = Integer.parseInt(scanner.nextLine().trim());
                    empleado = EmpleadoFactory.crearEmpleado(tipo, nombres, primerApellido, segundoApellido, direccion,
                            fechaNacimiento, sexo, telefono, email, departamento, tipoSupervision, numSubordinados,
                            lideraEquipo, diasTrabajados, incidentesResueltos, totalIncidentes);
                    break;

                case TECNICO:
                    System.out.print("Especialidad: ");
                    String especialidad = scanner.nextLine().trim();
                    System.out.print("Horas Extras al Mes: ");
                    int horasExtras = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Turno (Mañana/Tarde/Noche): ");
                    String turno = scanner.nextLine().trim();
                    System.out.print("¿Es Líder? (S/N): ");
                    boolean esLider = scanner.nextLine().trim().toUpperCase().startsWith("S");
                    System.out.print("Certificaciones (separadas por coma, o vacío): ");
                    String certificacionesInput = scanner.nextLine().trim();
                    List<String> certificaciones = certificacionesInput.isEmpty() ? new ArrayList<>() : List.of(certificacionesInput.split(","));
                    empleado = EmpleadoFactory.crearEmpleado(tipo, nombres, primerApellido, segundoApellido, direccion,
                            fechaNacimiento, sexo, telefono, email, especialidad, horasExtras, turno, esLider, certificaciones);
                    break;

                default:
                    throw new IllegalArgumentException("Tipo de empleado no soportado.");
            }

            // Establecer salario base (suponiendo que Empleado tiene un método setSalarioBase)
            try {
                empleado.setSueldoBase(salarioBase); // Cambiar setSalarioBase por setSueldoBase
            } catch (Exception e) {
                throw new IllegalArgumentException("Error al establecer el salario base: " + e.getMessage());
            }

            // Agregar al repositorio
            repository.agregarEmpleado(empleado);
            System.out.println("Empleado " + empleado.getNombreCompleto() + " agregado exitosamente.");

        } catch (DateTimeParseException e) {
            System.out.println("Formato de fecha inválido. Use YYYY-MM-DD (ejemplo: 1990-05-15).");
        } catch (NumberFormatException e) {
            System.out.println("Entrada numérica inválida. Por favor, ingrese un número válido.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado al agregar empleado: " + e.getMessage());
        }
    }
}