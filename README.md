# ElysiumHR  

<div>
<img src="ElysiumHR -logo.png" alt="ElysiumHR logo" align="right" width="360">

Bienvenido al **ElysiumHR,** el sistema de gestión de empleados creado con Java, diseñado para gestionar información de empleados en una empresa. Este sistema permite cargar datos iniciales de empleados, calcular sueldos netos con bonificaciones y descuentos, ordenar empleados por diferentes criterios, y generar reportes básicos. 
<br>La aplicación está estructurada en una arquitectura modular para facilitar el mantenimiento y la escalabilidad, con soporte para crecimiento futuro, como la adición de nuevos empleados y la gestión de equipos de trabajo.
</div>
<br>

## Características
- **Gestión de empleados**: Soporta diferentes roles (Gerente, Jefe de Área, Supervisor, Técnico) con atributos específicos y cálculos personalizados de bonificaciones.
- **Carga inicial de datos**: Inicializa el sistema con 25 empleados predefinidos (2 Gerentes, 3 Jefes de Área, 5 Supervisores, 15 Técnicos).
- **Cálculo de nómina**: Calcula sueldos netos considerando bonificaciones (por metas alcanzadas, incidentes resueltos, horas extras, certificaciones, liderazgo) y descuentos legales (ISSS 7.5%, AFP 7.75%, renta).
- **Gestión de equipos de trabajo**: Permite asignar y gestionar equipos a cargo para Gerentes, Jefes de Área, y Supervisores, con soporte para agregar o remover equipos.
- **Escalabilidad**: Diseñado para soportar el crecimiento de la empresa, con la capacidad de agregar nuevos empleados dinámicamente a través de `EmpleadoRepository` y `EmpleadoFactory`.
- **Ordenamiento y reportes**: Ordena empleados por sueldo neto o apellido, y muestra estadísticas como la cantidad de empleados por rol.
- **Interfaz de consola**: Menú interactivo para listar empleados, ordenarlos, y generar reportes básicos.
- **Nota**: Las funcionalidades de bonificaciones, gestión de equipos, y adición de empleados están implementadas en el código, pero no están integradas en el menú interactivo actual. Estas características pueden ser activadas extendiendo el menú o desarrollando nuevas opciones en "SistemaEmpleadosApp".


### Descripción de Paquetes
- **`com.empresa.empleados.model`**: Contiene las clases que representan los empleados, con `Empleado` como clase abstracta base y subclases para cada rol (`Gerente`, `JefeArea`, `Supervisor`, `Tecnico`). Cada clase define atributos específicos (por ejemplo, equipos a cargo, certificaciones) y lógica para calcular bonificaciones.
- **`com.empresa.empleados.service`**: Maneja la lógica de negocio, como cálculos de nómina (`NominaService`), gestión de empleados (`EmpleadoService`), y descuentos legales (`CalculadoraDescuentos`).
- **`com.empresa.empleados.data`**: Gestiona el almacenamiento y carga de datos. `EmpleadoRepository` simula una base de datos en memoria con soporte para agregar nuevos empleados, `DataLoader` inicializa empleados, y `EmpleadoFactory` crea instancias de empleados según su tipo.
- **`com.empresa.empleados.utils`**: Incluye utilidades como ordenamiento de empleados (`OrdenadorEmpleados`), validación de datos (`ValidadorDatos`), y formateo de reportes (`FormateadorReportes`).
- **`com.empresa.empleados.ui`**: Contiene la lógica de la interfaz de usuario, con clases para el menú de consola (`MenuPrincipal`, `MenuConsola`) y generación de reportes (`GeneradorReportes`).
- **`com.empresa.empleados.main`**: Punto de entrada de la aplicación (`SistemaEmpleadosApp`), que inicializa el sistema y muestra el menú interactivo.

## Requisitos
- **Java**: Versión 8 o superior (desarrollado con Java 21).
- **Entorno**: No requiere dependencias externas; usa solo la biblioteca estándar de Java.
- **IDE (opcional)**: Compatible con Eclipse, IntelliJ IDEA, o cualquier editor que soporte Java.
