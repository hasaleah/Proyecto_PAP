package com.empresa.empleados.data;

import com.empresa.empleados.model.Empleado;
import com.empresa.empleados.model.Gerente;
import com.empresa.empleados.model.JefeArea;
import com.empresa.empleados.model.Supervisor;
import com.empresa.empleados.model.Tecnico;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Repositorio para gestionar la persistencia de empleados
 * Proporciona operaciones CRUD y consultas específicas
 */
public class EmpleadoRepository {
    
    private final List<Empleado> empleados;
    
    /**
     * Constructor que inicializa la lista de empleados
     */
    public EmpleadoRepository() {
        this.empleados = new ArrayList<>();
    }
    
    /**
     * Constructor que inicializa el repositorio con una lista de empleados
     * @param empleados Lista inicial de empleados
     */
    public EmpleadoRepository(List<Empleado> empleados) {
        if (empleados == null) {
            this.empleados = new ArrayList<>();
        } else {
            this.empleados = new ArrayList<>(empleados);
        }
    }
    
    // =============== OPERACIONES CRUD ===============
    
    /**
     * Agrega un nuevo empleado al repositorio
     * @param empleado Empleado a agregar
     * @return true si se agregó correctamente
     */
    public boolean agregarEmpleado(Empleado empleado) {
        if (empleado == null || existeEmpleado(empleado.getNombreCompleto())) {
            return false;
        }
        return empleados.add(empleado);
    }
    
    /**
     * Agrega múltiples empleados al repositorio
     * @param nuevosEmpleados Lista de empleados a agregar
     * @return Número de empleados agregados
     */
    public int agregarEmpleados(List<Empleado> nuevosEmpleados) {
        if (nuevosEmpleados == null) {
            return 0;
        }
        int agregados = 0;
        for (Empleado empleado : nuevosEmpleados) {
            if (agregarEmpleado(empleado)) {
                agregados++;
            }
        }
        return agregados;
    }
    
    /**
     * Busca un empleado por su nombre completo (coincidencia exacta, ignora mayúsculas)
     * @param nombreCompleto Nombre completo del empleado
     * @return Optional con el empleado encontrado, o vacío si no existe
     */
    public Optional<Empleado> buscarPorNombreCompleto(String nombreCompleto) {
        if (nombreCompleto == null || nombreCompleto.trim().isEmpty()) {
            return Optional.empty();
        }
        String nombreBusqueda = nombreCompleto.trim().toLowerCase();
        return empleados.stream()
                .filter(e -> e.getNombreCompleto().toLowerCase().equals(nombreBusqueda))
                .findFirst();
    }
    
    /**
     * Actualiza un empleado existente (basado en nombre completo)
     * @param empleadoActualizado Empleado con los datos actualizados
     * @return true si se actualizó correctamente
     */
    public boolean actualizarEmpleado(Empleado empleadoActualizado) {
        if (empleadoActualizado == null) {
            return false;
        }
        Optional<Empleado> existente = buscarPorNombreCompleto(empleadoActualizado.getNombreCompleto());
        if (existente.isPresent()) {
            int index = empleados.indexOf(existente.get());
            empleados.set(index, empleadoActualizado);
            return true;
        }
        return false;
    }
    
    /**
     * Elimina un empleado por su nombre completo
     * @param nombreCompleto Nombre completo del empleado
     * @return true si se eliminó correctamente
     */
    public boolean eliminarEmpleado(String nombreCompleto) {
        Optional<Empleado> empleado = buscarPorNombreCompleto(nombreCompleto);
        return empleado.isPresent() && empleados.remove(empleado.get());
    }
    
    /**
     * Obtiene todos los empleados registrados
     * @return Lista de empleados
     */
    public List<Empleado> obtenerTodosLosEmpleados() {
        return new ArrayList<>(empleados);
    }
    
    /**
     * Obtiene el número total de empleados
     * @return Total de empleados
     */
    public int obtenerTotalEmpleados() {
        return empleados.size();
    }
    
    /**
     * Limpia todos los empleados del repositorio
     */
    public void limpiarRepositorio() {
        empleados.clear();
    }
    
    // =============== CONSULTAS ESPECÍFICAS ===============
    
    /**
     * Busca empleados por nombre (coincidencia parcial, ignora mayúsculas)
     * @param nombre Nombre o parte del nombre a buscar
     * @return Lista de empleados que coinciden
     */
    public List<Empleado> buscarPorNombreParcial(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return new ArrayList<>();
        }
        String nombreBusqueda = nombre.trim().toLowerCase();
        return empleados.stream()
                .filter(e -> e.getNombreCompleto().toLowerCase().contains(nombreBusqueda))
                .collect(Collectors.toList());
    }
    
    /**
     * Busca empleados por primer apellido (coincidencia exacta, ignora mayúsculas)
     * @param apellido Primer apellido a buscar
     * @return Lista de empleados que coinciden
     */
    public List<Empleado> buscarPorPrimerApellido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) {
            return new ArrayList<>();
        }
        String apellidoBusqueda = apellido.trim().toLowerCase();
        return empleados.stream()
                .filter(e -> e.getPrimerApellido().toLowerCase().equals(apellidoBusqueda))
                .collect(Collectors.toList());
    }
    
    /**
     * Obtiene empleados por tipo/rol
     * @param tipoEmpleado Tipo de empleado (GERENTE, JEFE DE ÁREA, SUPERVISOR, TÉCNICO)
     * @return Lista de empleados del tipo especificado
     */
    public List<Empleado> obtenerPorTipo(String tipoEmpleado) {
        if (tipoEmpleado == null) {
            return new ArrayList<>();
        }
        return empleados.stream()
                .filter(e -> e.getTipoEmpleado().equalsIgnoreCase(tipoEmpleado))
                .collect(Collectors.toList());
    }
    
    /**
     * Obtiene solo los gerentes
     * @return Lista de gerentes
     */
    public List<Gerente> obtenerGerentes() {
        return empleados.stream()
                .filter(e -> e instanceof Gerente)
                .map(e -> (Gerente) e)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtiene solo los jefes de área
     * @return Lista de jefes de área
     */
    public List<JefeArea> obtenerJefesArea() {
        return empleados.stream()
                .filter(e -> e instanceof JefeArea)
                .map(e -> (JefeArea) e)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtiene solo los supervisores
     * @return Lista de supervisores
     */
    public List<Supervisor> obtenerSupervisores() {
        return empleados.stream()
                .filter(e -> e instanceof Supervisor)
                .map(e -> (Supervisor) e)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtiene solo los técnicos
     * @return Lista de técnicos
     */
    public List<Tecnico> obtenerTecnicos() {
        return empleados.stream()
                .filter(e -> e instanceof Tecnico)
                .map(e -> (Tecnico) e)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtiene empleados que cumplen años en un mes específico
     * @param mes Mes del año (1-12)
     * @return Lista de empleados que cumplen años en el mes
     */
    public List<Empleado> obtenerCumpleañosPorMes(int mes) {
        if (mes < 1 || mes > 12) {
            return new ArrayList<>();
        }
        return empleados.stream()
                .filter(e -> e.getFechaNacimiento().getMonthValue() == mes)
                .collect(Collectors.toList());
    }
    
    // =============== VALIDACIONES ===============
    
    /**
     * Verifica si existe un empleado con el mismo nombre completo
     * @param nombreCompleto Nombre completo del empleado
     * @return true si el empleado existe
     */
    public boolean existeEmpleado(String nombreCompleto) {
        return buscarPorNombreCompleto(nombreCompleto).isPresent();
    }
}