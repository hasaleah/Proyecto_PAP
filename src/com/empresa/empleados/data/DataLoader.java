package com.empresa.empleados.data;

import com.empresa.empleados.model.Empleado;
import com.empresa.empleados.model.Gerente;
import com.empresa.empleados.model.JefeArea;
import com.empresa.empleados.model.Supervisor;
import com.empresa.empleados.model.Tecnico;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase responsable de cargar datos iniciales de empleados
 * Pobla el repositorio con empleados predefinidos
 */
public class DataLoader {
    
    private final EmpleadoRepository repository;
    
    /**
     * Constructor que inicializa el cargador con un repositorio
     * @param repository Repositorio donde se almacenarán los empleados
     */
    public DataLoader(EmpleadoRepository repository) {
        if (repository == null) {
            throw new IllegalArgumentException("El repositorio no puede ser nulo");
        }
        this.repository = repository;
    }
    
    /**
     * Carga los datos iniciales de empleados en el repositorio
     * Crea 2 gerentes, 3 jefes de área, 5 supervisores y 15 técnicos
     * @return Número de empleados cargados exitosamente
     */
    public int cargarDatosIniciales() {
        List<Empleado> empleadosIniciales = new ArrayList<>();
        
     // Gerentes (2)
        Gerente gerente1 = new Gerente(
            "Ana Sofía", "Gómez", "Martínez",
            "Calle Principal 123, San Salvador", LocalDate.of(1975, 3, 15), 'F',
            "1234-5678", "ana.gomez@empresa.com",
            "Dirección General", 2, true
        );
        gerente1.agregarEquipoACargo("Operaciones");
        gerente1.agregarEquipoACargo("Finanzas");
        empleadosIniciales.add(gerente1);

        Gerente gerente2 = new Gerente(
            "Carlos Eduardo", "López", "Reyes",
            "Avenida Norte 456, Santa Tecla", LocalDate.of(1970, 8, 22), 'M',
            "8765-4321", "carlos.lopez@empresa.com",
            "Dirección Comercial", 3, true
        );
        gerente2.agregarEquipoACargo("Ventas");
        gerente2.agregarEquipoACargo("Marketing");
        empleadosIniciales.add(gerente2);
        
        
     // Jefes de Área (3)
        JefeArea jefe1 = new JefeArea(
            "María Elena", "Cruz", "Sánchez",
            "Colonia Escalón, San Salvador", LocalDate.of(1980, 5, 10), 'F',
            "2345-6789", "maria.cruz@empresa.com",
            "Operaciones", "Operativa", 10, true
        );
        jefe1.agregarSubArea("Producción");
        jefe1.agregarSubArea("Logística");
        jefe1.setPresupuestoAnualACargo(500000.00);
        jefe1.setMetasAlcanzadas(8);
        jefe1.setTotalMetas(10);
        empleadosIniciales.add(jefe1);

        JefeArea jefe2 = new JefeArea(
            "José Antonio", "Morales", "Vásquez",
            "San Benito, San Salvador", LocalDate.of(1978, 11, 30), 'M',
            "3456-7890", "jose.morales@empresa.com",
            "Finanzas", "Administrativa", 8, true
        );
        jefe2.agregarSubArea("Contabilidad");
        jefe2.agregarSubArea("Presupuestos");
        jefe2.setPresupuestoAnualACargo(300000.00);
        jefe2.setMetasAlcanzadas(6);
        jefe2.setTotalMetas(8);
        empleadosIniciales.add(jefe2);

        JefeArea jefe3 = new JefeArea(
            "Laura Beatriz", "Hernández", "Pérez",
            "Colonia Flor Blanca, San Salvador", LocalDate.of(1982, 2, 18), 'F',
            "4567-8901", "laura.hernandez@empresa.com",
            "Ventas", "Comercial", 12, false
        );
        jefe3.agregarSubArea("Retail");
        jefe3.agregarSubArea("Corporativo");
        jefe3.setPresupuestoAnualACargo(200000.00);
        jefe3.setMetasAlcanzadas(7);
        jefe3.setTotalMetas(10);
        empleadosIniciales.add(jefe3);
        
     // Supervisores (5)
        Supervisor supervisor1 = new Supervisor(
         "Ricardo Andrés", "Ramírez", "García",
         "Mejicanos, San Salvador", LocalDate.of(1985, 7, 12), 'M',
         "5678-9012", "ricardo.ramirez@empresa.com",
         "Producción", "Directa", 5, true
        );
        supervisor1.setDiasTrabajadosMes(20);
        supervisor1.setIncidentesResueltos(18);
        supervisor1.setTotalIncidentes(20);
        empleadosIniciales.add(supervisor1);

        Supervisor supervisor2 = new Supervisor(
         "Carmen Julia", "Díaz", "Mendoza",
         "Soyapango, San Salvador", LocalDate.of(1983, 4, 25), 'F',
         "6789-0123", "carmen.diaz@empresa.com",
         "Logística", "Indirecta", 4, false
        );
        supervisor2.setDiasTrabajadosMes(22);
        supervisor2.setIncidentesResueltos(15);
        supervisor2.setTotalIncidentes(15);
        empleadosIniciales.add(supervisor2);

        Supervisor supervisor3 = new Supervisor(
         "Miguel Ángel", "Torres", "Castro",
         "Apopa, San Salvador", LocalDate.of(1987, 9, 5), 'M',
         "7890-1234", "miguel.torres@empresa.com",
         "Contabilidad", "Mixta", 3, true
        );
        supervisor3.setDiasTrabajadosMes(18);
        supervisor3.setIncidentesResueltos(12);
        supervisor3.setTotalIncidentes(15);
        empleadosIniciales.add(supervisor3);

        Supervisor supervisor4 = new Supervisor(
         "Sonia Patricia", "Flores", "Rivas",
         "Santa Tecla, La Libertad", LocalDate.of(1984, 12, 1), 'F',
         "8901-2345", "sonia.flores@empresa.com",
         "Ventas Retail", "Directa", 6, false
        );
        supervisor4.setDiasTrabajadosMes(21);
        supervisor4.setIncidentesResueltos(20);
        supervisor4.setTotalIncidentes(22);
        empleadosIniciales.add(supervisor4);

        Supervisor supervisor5 = new Supervisor(
         "David Ernesto", "Vega", "Ortiz",
         "San Miguel, San Miguel", LocalDate.of(1986, 6, 20), 'M',
         "9012-3456", "david.vega@empresa.com",
         "Marketing", "Indirecta", 4, true
        );
        supervisor5.setDiasTrabajadosMes(19);
        supervisor5.setIncidentesResueltos(14);
        supervisor5.setTotalIncidentes(16);
        empleadosIniciales.add(supervisor5);
        
     
        // Técnicos (15)
        Tecnico tecnico1 = new Tecnico(
            "Juan Carlos", "Pineda", "Alvarado",
            "Cuscatancingo, San Salvador", LocalDate.of(1990, 1, 14), 'M',
            "123456789", "juan.pineda@empresa.com",
            "Mecánica", 5, "Mañana", true
        );
        tecnico1.agregarCertificacion("Certificación ISO 9001");
        tecnico1.setHorasExtrasMes(10);
        empleadosIniciales.add(tecnico1);

        Tecnico tecnico2 = new Tecnico(
            "Gabriela Alejandra", "Molina", "Mendéz",
            "Ilopango, San Salvador", LocalDate.of(1992, 3, 12), 'F',
            "234567890", "gabriela.molina@empresa.com",
            "Electrónica", 3, "Noche", false
        );
        tecnico2.agregarCertificacion("Certificación Cisco");
        tecnico2.setHorasExtrasMes(15);
        empleadosIniciales.add(tecnico2);

        Tecnico tecnico3 = new Tecnico(
            "Luis Fernando", "Rivas", "Guzmán",
            "San Marcos, San Salvador", LocalDate.of(1989, 10, 27), 'M',
            "345678901", "luis.rivas@empresa.com",
            "Informática", 6, "Mixto", true
        );
        tecnico3.agregarCertificacion("Certificación CompTIA");
        tecnico3.setHorasExtrasMes(12);
        empleadosIniciales.add(tecnico3);

        Tecnico tecnico4 = new Tecnico(
            "Verónica Lissette", "Campos", "López",
            "Ayutuxtepeque, San Salvador", LocalDate.of(1991, 5, 19), 'F',
            "456789012", "veronica.campos@empresa.com",
            "Mantenimiento", 4, "Tarde", false
        );
        tecnico4.agregarCertificacion("Certificación OSHA");
        tecnico4.setHorasExtrasMes(8);
        empleadosIniciales.add(tecnico4);

        Tecnico tecnico5 = new Tecnico(
            "Óscar Mauricio", "Santos", "Mejía",
            "Delgado, San Salvador", LocalDate.of(1993, 8, 3), 'M',
            "567890123", "oscar.santos@empresa.com",
            "Redes", 2, "Mañana", true
        );
        tecnico5.agregarCertificacion("Certificación CCNA");
        tecnico5.setHorasExtrasMes(20);
        empleadosIniciales.add(tecnico5);

        Tecnico tecnico6 = new Tecnico(
            "Claudia Marcela", "Aguilar", "Romero",
            "Mejicanos, San Salvador", LocalDate.of(1990, 11, 11), 'F',
            "678901234", "claudia.aguilar@empresa.com",
            "Automatización", 7, "Noche", false
        );
        tecnico6.agregarCertificacion("Certificación PLC");
        tecnico6.setHorasExtrasMes(5);
        empleadosIniciales.add(tecnico6);

        Tecnico tecnico7 = new Tecnico(
            "Roberto Daniel", "Cortez", "Flores",
            "Soyapango, San Salvador", LocalDate.of(1988, 4, 7), 'M',
            "789012345", "roberto.cortez@empresa.com",
            "Mecánica", 8, "Mixto", true
        );
        tecnico7.agregarCertificacion("Certificación ASME");
        tecnico7.setHorasExtrasMes(18);
        empleadosIniciales.add(tecnico7);

        Tecnico tecnico8 = new Tecnico(
            "Isabel Cristina", "García", "Vides",
            "Apopa, San Salvador", LocalDate.of(1994, 6, 29), 'F',
            "890123456", "isabel.garcia@empresa.com",
            "Electrónica", 1, "Tarde", false
        );
        tecnico8.agregarCertificacion("Certificación IPC");
        tecnico8.setHorasExtrasMes(10);
        empleadosIniciales.add(tecnico8);

        Tecnico tecnico9 = new Tecnico(
            "Héctor Manuel", "Martínez", "Serrano",
            "Santa Tecla, La Libertad", LocalDate.of(1987, 2, 13), 'M',
            "901234567", "hector.martinez@empresa.com",
            "Informática", 5, "Mañana", true
        );
        tecnico9.agregarCertificacion("Certificación Microsoft");
        tecnico9.setHorasExtrasMes(15);
        empleadosIniciales.add(tecnico9);

        Tecnico tecnico10 = new Tecnico(
            "Mónica Alejandra", "Pérez", "Cáceres",
            "San Miguel, San Miguel", LocalDate.of(1991, 9, 17), 'F',
            "123456780", "monica.perez@empresa.com",
            "Mantenimiento", 3, "Noche", false
        );
        tecnico10.agregarCertificacion("Certificación NEBOSH");
        tecnico10.setHorasExtrasMes(12);
        empleadosIniciales.add(tecnico10);

        Tecnico tecnico11 = new Tecnico(
            "Francisco Javier", "Gómez", "Ruiz",
            "San Salvador, San Salvador", LocalDate.of(1989, 12, 4), 'M',
            "234567891", "francisco.gomez@empresa.com",
            "Redes", 4, "Mixto", true
        );
        tecnico11.agregarCertificacion("Certificación Fortinet");
        tecnico11.setHorasExtrasMes(10);
        empleadosIniciales.add(tecnico11);

        Tecnico tecnico12 = new Tecnico(
            "Patricia Lorena", "Vásquez", "Méndez",
            "Santa Ana, Santa Ana", LocalDate.of(1992, 7, 9), 'F',
            "345678902", "patricia.vasquez@empresa.com",
            "Automatización", 2, "Tarde", false
        );
        tecnico12.agregarCertificacion("Certificación Siemens");
        tecnico12.setHorasExtrasMes(8);
        empleadosIniciales.add(tecnico12);

        Tecnico tecnico13 = new Tecnico(
            "Eduardo José", "Alvarado", "Cruz",
            "Sonsonate, Sonsonate", LocalDate.of(1990, 3, 23), 'M',
            "456789013", "eduardo.alvarado@empresa.com",
            "Mecánica", 6, "Mañana", true
        );
        tecnico13.agregarCertificacion("Certificación API");
        tecnico13.setHorasExtrasMes(14);
        empleadosIniciales.add(tecnico13);

        Tecnico tecnico14 = new Tecnico(
            "Lorena Elizabeth", "Chávez", "Rivas",
            "Usulután, Usulután", LocalDate.of(1993, 1, 30), 'F',
            "567890124", "lorena.chavez@empresa.com",
            "Electrónica", 3, "Noche", false
        );
        tecnico14.agregarCertificacion("Certificación IEEE");
        tecnico14.setHorasExtrasMes(16);
        empleadosIniciales.add(tecnico14);

        Tecnico tecnico15 = new Tecnico(
            "Jorge Alberto", "Mendoza", "Torres",
            "Ahuachapán, Ahuachapán", LocalDate.of(1988, 10, 15), 'M',
            "678901245", "jorge.mendoza@empresa.com",
            "Informática", 5, "Mixto", true
        );
        tecnico15.agregarCertificacion("Certificación AWS");
        tecnico15.setHorasExtrasMes(13);
        empleadosIniciales.add(tecnico15);
        
        // Agregar empleados al repositorio
        return repository.agregarEmpleados(empleadosIniciales);
    }
    
    /**
     * Obtiene la lista de empleados cargados
     * @return Lista de empleados
     */
    public List<Empleado> obtenerEmpleadosCargados() {
        return repository.obtenerTodosLosEmpleados();
    }
    
    /**
     * Limpia el repositorio y recarga los datos iniciales
     * @return Número de empleados cargados
     */
    public int recargarDatosIniciales() {
        repository.limpiarRepositorio();
        return cargarDatosIniciales();
    }
}