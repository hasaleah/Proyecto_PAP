# Sistema de Gestión de Empleados

Bienvenido al **Sistema de Gestión de Empleados**, una aplicación Java diseñada para gestionar información de empleados en una empresa. Este sistema permite cargar datos iniciales de empleados, calcular sueldos netos con bonificaciones y descuentos, ordenar empleados por diferentes criterios (sueldo, apellido), y generar reportes básicos. La aplicación está estructurada en una arquitectura modular, con separación clara entre modelo, lógica de negocio, manejo de datos, utilidades, e interfaz de usuario.

## Características
- **Gestión de empleados**: Soporta diferentes roles (Gerente, Jefe de Área, Supervisor, Técnico) con atributos específicos y cálculos personalizados de bonificaciones.
- **Carga inicial de datos**: Inicializa el sistema con 25 empleados predefinidos (2 Gerentes, 3 Jefes de Área, 5 Supervisores, 15 Técnicos).
- **Cálculo de nómina**: Calcula sueldos netos considerando bonificaciones (por metas, incidentes resueltos, horas extras, etc.) y descuentos (ISSS, AFP, renta).
- **Ordenamiento y reportes**: Permite ordenar empleados por sueldo neto o apellido, y muestra estadísticas como la cantidad de empleados por rol.
- **Interfaz de consola**: Menú interactivo para listar empleados, ordenarlos, y generar reportes básicos.


### Descripción de Paquetes
- **`com.empresa.empleados.model`**: Contiene las clases que representan los empleados, con `Empleado` como clase abstracta base y subclases para cada rol (`Gerente`, `JefeArea`, `Supervisor`, `Tecnico`). Cada clase define atributos específicos y lógica para calcular bonificaciones.
- **`com.empresa.empleados.service`**: Maneja la lógica de negocio, como cálculos de nómina (`NominaService`), gestión de empleados (`EmpleadoService`), y descuentos legales (`CalculadoraDescuentos`).
- **`com.empresa.empleados.data`**: Gestiona el almacenamiento y carga de datos. `EmpleadoRepository` simula una base de datos en memoria, `DataLoader` inicializa empleados, y `EmpleadoFactory` crea instancias de empleados según su tipo.
- **`com.empresa.empleados.utils`**: Incluye utilidades como ordenamiento de empleados (`OrdenadorEmpleados`), validación de datos (`ValidadorDatos`), y formateo de reportes (`FormateadorReportes`).
- **`com.empresa.empleados.ui`**: Contiene la lógica de la interfaz de usuario, con clases para el menú de consola (`MenuPrincipal`, `MenuConsola`) y generación de reportes (`GeneradorReportes`).
- **`com.empresa.empleados.main`**: Punto de entrada de la aplicación (`SistemaEmpleadosApp`), que inicializa el sistema y muestra el menú interactivo.

## Requisitos
- **Java**: Versión 8 o superior (desarrollado con Java 21).
- **Entorno**: No requiere dependencias externas; usa solo la biblioteca estándar de Java.
- **IDE (opcional)**: Compatible con Eclipse, IntelliJ IDEA, o cualquier editor que soporte Java.

## Instalación y Ejecución
1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/tu-usuario/sistema-empleados.git
   cd sistema-empleados
